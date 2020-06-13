package com.notspend.service.impl.sync;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notspend.entity.Account;
import com.notspend.entity.Category;
import com.notspend.entity.Expense;
import com.notspend.exception.AccountSyncFailedException;
import com.notspend.service.AccountService;
import com.notspend.service.CategoryService;
import com.notspend.service.CurrencyService;
import com.notspend.service.ExpenseService;
import com.notspend.service.ExpenseSyncService;
import com.notspend.service.MccService;
import com.notspend.util.TimeHelper;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class MonobankSyncServiceImpl implements ExpenseSyncService {

    private static final int TEN_MINUTES = 10 * 60; //sec

    private final ExpenseService expenseService;

    private final CurrencyService currencyService;

    private final CategoryService categoryService;

    private final AccountService accountService;

    private final MccService mccService;

    @Autowired
    public MonobankSyncServiceImpl(ExpenseService expenseService, CurrencyService currencyService,
                                   CategoryService categoryService, AccountService accountService,
                                   MccService mccService) {
        this.expenseService = expenseService;
        this.currencyService = currencyService;
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.mccService = mccService;
    }

    @Override
    public void syncDataWithBankServer(List<Account> accounts) throws AccountSyncFailedException {
        log.info("Start sync for user '" + accounts.get(0).getUser().getUsername() + "'.");
        if (accounts == null || accounts.isEmpty()){
            throw new AccountSyncFailedException("List with accounts are empty or null");
        }

        List<Thread> accountWithTokensToSync = accounts.stream()
                .filter(a -> a.getToken() != null && !a.getToken().isEmpty())
                .map(RunnableMono::new)
                .map(DelegatingSecurityContextRunnable::new)
                .map(Thread::new)
                .collect(Collectors.toList());

        if (accountWithTokensToSync.isEmpty()) {
            throw new AccountSyncFailedException("There is no accounts to sync");
        }

        new Thread(() -> {
            for (Thread account : accountWithTokensToSync){
                account.start();
                try {
                    account.join();
                } catch (InterruptedException e) {
                    log.error("Can't synchronize account", e);
                }
            }
        }).start();
    }

    class RunnableMono implements Runnable {

        private static final int DEFAULT_CARD_NUMBER = 0;

        //Limitation comes from Monobank API
        private static final int MAX_MONOBANK_STATEMENT_TIME_IN_SECONDS = 2682000; // s (1 month)

        private static final int DELAY_BETWEEN_REQUEST = 70000; // ms

        private static final String MONOBANK_API_ENDPOINT = "https://api.monobank.ua/";

        private static final int MAXIMUM_MONTHS_TO_UPLOAD = 12;

        private Account account;

        RunnableMono(Account account) {
            this.account = account;
        }

        @Override
        public void run() {

            long delayBetweenSync = TimeHelper.getCurrentEpochTime() - account.getSynchronizationTime();

            if (delayBetweenSync < TEN_MINUTES){
                //if last sync was 10 min ago don't need to sync again
                account.setSynchronizationTime(TimeHelper.getCurrentEpochTime());
                accountService.updateAccount(account);
                return;
            }

            if (account.getSynchronizationTime() == null){
                //this mean it's first time sync for this account
                //so, we can define last sync id and set current time for last sync
                String lastTransactionId = null;
                try {
                    lastTransactionId = getLastTransactionId();
                } catch (Exception e) {
                    log.warn("Can't get last transaction id" + e);
                }
                    account.setSynchronizationId(lastTransactionId);
                    account.setSynchronizationTime(TimeHelper.getCurrentEpochTime());
                    accountService.updateAccount(account);
                return;
            }

            List<MonobankStatementAnswer> monobankStatementAnswers = null;
            long currentEpochTime = TimeHelper.getCurrentEpochTime();
            long epochTimeFrom = currentEpochTime - MAX_MONOBANK_STATEMENT_TIME_IN_SECONDS;

            int uploadMonthCounter = 0;

            String firstSuccessfulSyncId = null;
            String lastSuccessSyncId = account.getSynchronizationId();

            while (uploadMonthCounter < MAXIMUM_MONTHS_TO_UPLOAD){

                try {
                    monobankStatementAnswers = getStatements(epochTimeFrom, currentEpochTime);
                } catch (Exception e) {
                    log.error("Can't retrieve statements.", e);
                }

                for (MonobankStatementAnswer monobankExpense : monobankStatementAnswers){
                    if (!monobankExpense.getId().equals(lastSuccessSyncId)){
                        String mccCategoryName = mccService.getCategoryByMccCode(monobankExpense.getMcc());
                        if (mccCategoryName.isEmpty()) {
                            continue;
                        }
                        Expense expense = new Expense();
                        expense.setUser(account.getUser());
                        expense.setAccount(account);
                        expense.setUser(account.getUser());
                        expense.setCurrency(account.getCurrency());
                        expense.setComment(monobankExpense.getDescription());
                        expense.setSum(-(monobankExpense.getAmount() / 100d));

                        List<Category> categories = categoryService.getAllExpenseCategories();
                        Category category = categories.stream()
                                .filter(c -> c.getName().equalsIgnoreCase(mccCategoryName))
                                .findFirst()
                                .orElse((new Category()));

                        if (category.getCategoryId() == 0){
                            category.setUser(account.getUser());
                            category.setIncome(false);
                            category.setName(mccCategoryName);
                            categoryService.addCategory(category);
                        }

                        expense.setCategory(categoryService.getCategory(category.getName()));

                        long epochTime = monobankExpense.getTime();
                        LocalDate date = LocalDate.ofInstant(Instant.ofEpochSecond(epochTime), ZoneId.systemDefault());
                        expense.setDate(date);
                        expense.setTime(LocalTime.ofSecondOfDay(epochTime % 3600));

                        expenseService.addExpense(expense);
                        if (firstSuccessfulSyncId == null){
                            firstSuccessfulSyncId = monobankExpense.getId();
                        }
                    } else {
                        //we find last sync id
                        if (firstSuccessfulSyncId != null){
                            account.setSynchronizationId(firstSuccessfulSyncId);
                        }
                        account.setSynchronizationTime(TimeHelper.getCurrentEpochTime());
                        accountService.updateAccount(account);
                        return;
                    }
                }
                epochTimeFrom -= MAX_MONOBANK_STATEMENT_TIME_IN_SECONDS;
                currentEpochTime -= MAX_MONOBANK_STATEMENT_TIME_IN_SECONDS;
                uploadMonthCounter++;
                try {
                    Thread.sleep(DELAY_BETWEEN_REQUEST);
                } catch (InterruptedException e) {
                    log.warn("Interrupted", e);
                    Thread.currentThread().interrupt();
                }
            }
        }

        private synchronized List<MonobankStatementAnswer> getStatements(long timeFrom, long timeTo){
            ObjectMapper mapper = new ObjectMapper();
            List<MonobankStatementAnswer> monobankStatementAnswers;
            try {
                String jsonResponse = getJsonWithStatements(timeFrom,timeTo).orElseThrow();
                monobankStatementAnswers = mapper.readValue(jsonResponse, new TypeReference<List<MonobankStatementAnswer>>(){});
            } catch (Exception e) {
                log.error("Can't parse answer." + e);
                return Collections.emptyList();
            }
            return monobankStatementAnswers;
        }

        private synchronized Optional<String> getJsonWithStatements(long timeFrom, long timeTo){
            try (CloseableHttpClient client = HttpClients.createDefault()){
                HttpGet httpGet = new HttpGet(MONOBANK_API_ENDPOINT + "personal/statement/" + DEFAULT_CARD_NUMBER + "/" + timeFrom + "/" + timeTo);
                httpGet.setHeader("X-Token", account.getToken());

                HttpResponse response = client.execute(httpGet);
                ResponseHandler<String> handler = new BasicResponseHandler();
                return Optional.of(handler.handleResponse(response));
            } catch (Exception e) {
                log.error("Monobank server is down or some problem with Monobank API");
                return Optional.empty();
            }
        }

        private synchronized String getLastTransactionId(){
            LocalDateTime currentTime = LocalDateTime.now();
            ZoneId zoneId = ZoneId.systemDefault();
            long epochTimeTo = currentTime.atZone(zoneId).toEpochSecond();
            long epochTimeFrom = currentTime.minus(30, ChronoUnit.DAYS).atZone(zoneId).toEpochSecond();
            return getStatements(epochTimeFrom, epochTimeTo).stream().findFirst().orElseThrow().getId();
        }
    }
}
