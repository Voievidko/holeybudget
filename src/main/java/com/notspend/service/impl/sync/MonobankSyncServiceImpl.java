package com.notspend.service.impl.sync;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notspend.entity.Account;
import com.notspend.entity.Category;
import com.notspend.entity.Currency;
import com.notspend.entity.Expense;
import com.notspend.service.AccountService;
import com.notspend.service.CategoryService;
import com.notspend.service.CurrencyService;
import com.notspend.service.ExpenseService;
import com.notspend.service.ExpenseSyncService;
import com.notspend.service.MccService;
import com.notspend.util.TimeHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

@Service
public class MonobankSyncServiceImpl implements ExpenseSyncService {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MccService mccService;

    public MonobankSyncServiceImpl() {
    }

    @Override
    public void syncDataWithBankServer(List<Account> accounts) throws Exception {
        if (accounts == null || accounts.isEmpty()){
            throw new IllegalArgumentException("Can't synchronize accounts");
        }
        accounts.stream()
                .map(Account::getToken)
                .filter(token -> token!=null && !token.isEmpty())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't sync account. Sync token is empty or null"));

        //We have at least one synchronization token and can start sync for each account.
        accounts.forEach(OneThread::new);
    }

    class OneThread implements Runnable {

        private static final int DEFAULT_CARD_NUMBER = 0;

        //Limitation comes from Monobank API
        private static final int MAX_MONOBANK_STATEMENT_TIME_IN_SECONDS = 2682000; // 1 month

        private static final int DELAY_BETWEEN_REQUEST = 70000; // ms

        private static final String MONOBANK_API_ENDPOINT = "https://api.monobank.ua/";

        private static final int MAXIMUM_MONTHS_TO_UPLOAD = 12;

        private Account account;

        OneThread(Account account) {
            this.account = account;
            SecurityContext context = SecurityContextHolder.getContext();
            DelegatingSecurityContextRunnable wrappedRunnable = new DelegatingSecurityContextRunnable(this, context);
            Thread thread = new Thread(wrappedRunnable, "MONOBANK");
            thread.start();
        }

        @Override
        public void run() {
            long delayBetweenSync = TimeHelper.getCurrentEpochTime() - account.getSynchronizationTime();

            if (delayBetweenSync < 10 * 60){
                //if last sync was 10 min ago don't need to sync again
                account.setSynchronizationTime(TimeHelper.getCurrentEpochTime());
                return;
            }

            if (account.getSynchronizationTime() == null){
                //this mean it's first time sync for this account
                //so, we can define last sync id and set current time for last sync
                try {
                    //TODO: what if we don't have last transaction id?
                    String lastTransactionId = getLastTransactionId();
                    account.setSynchronizationId(lastTransactionId);
                    account.setSynchronizationTime(TimeHelper.getCurrentEpochTime());
                    accountService.updateAccount(account);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            List<MonobankStatementAnswer> monobankStatementAnswers = null;
            long currentEpochTime = TimeHelper.getCurrentEpochTime();
            long epochTimeFrom = currentEpochTime - MAX_MONOBANK_STATEMENT_TIME_IN_SECONDS;

            int uploadMonthCounter = 0;

            String firstSuccessfulSyncId = null;

            while (uploadMonthCounter < MAXIMUM_MONTHS_TO_UPLOAD){

                try {
                    monobankStatementAnswers = getStatements(epochTimeFrom, currentEpochTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (MonobankStatementAnswer monobankExpense : monobankStatementAnswers){
                    if (!monobankExpense.getId().equals(account.getSynchronizationId())){
                        String mccCategoryName = mccService.getCategoryByMccCode(monobankExpense.getMcc());
                        if (mccCategoryName.isEmpty()) {
                            continue;
                        }
                        Expense expense = new Expense();
                        expense.setUser(account.getUser());
                        expense.setAccount(account);
                        expense.setUser(account.getUser());
                        Integer currencyNumber = monobankExpense.getCurrencyCode();
                        expense.setCurrency(currencyService.getCurrencyByNumber(currencyNumber));
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
                        expense.setTime(LocalTime.ofSecondOfDay(epochTime%3600));

                        expenseService.addExpense(expense);
                        if (firstSuccessfulSyncId == null){
                            firstSuccessfulSyncId = monobankExpense.getId();
                        }
                    } else {
                        //we find last sync id
                        if (!(firstSuccessfulSyncId == null)){
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
                    e.printStackTrace();
                }
            }
        }

        private Currency getCurrencyByCode(List<Currency> currencyList, int code){
            return currencyList.stream().filter(a -> a.getCode().equals(String.valueOf(code))).findFirst().orElseThrow();
        }

        private List<MonobankStatementAnswer> getStatements(long timeFrom, long timeTo) throws Exception{
            ObjectMapper mapper = new ObjectMapper();
            List<MonobankStatementAnswer> monobankStatementAnswers;
            try {
                String jsonResponse = getJsonWithStatements(timeFrom,timeTo).orElseThrow();
                monobankStatementAnswers = mapper.readValue(jsonResponse, new TypeReference<List<MonobankStatementAnswer>>(){});
            } catch (Exception e) {
                //TODO: Log error here
                System.out.println("Can't parse answer");
                return Collections.emptyList();
            }
            return monobankStatementAnswers;
        }

        public Optional<String> getJsonWithStatements(long timeFrom, long timeTo){
            try (CloseableHttpClient client = HttpClients.createDefault()){
                HttpGet httpGet = new HttpGet(MONOBANK_API_ENDPOINT + "personal/statement/" + DEFAULT_CARD_NUMBER + "/" + timeFrom + "/" + timeTo);
                httpGet.setHeader("X-Token", account.getToken());

                HttpResponse response = client.execute(httpGet);
                ResponseHandler<String> handler = new BasicResponseHandler();
                return Optional.of(handler.handleResponse(response));
            } catch (Exception e) {
                //TODO: log it
                System.out.println("Monobank server is down or some problem with Monobank API");
                return Optional.empty();
            }
        }

        public String getLastTransactionId() throws Exception {
            LocalDateTime currentTime = LocalDateTime.now();
            ZoneId zoneId = ZoneId.systemDefault();
            long epochTimeTo = currentTime.atZone(zoneId).toEpochSecond();
            long epochTimeFrom = currentTime.minus(30, ChronoUnit.DAYS).atZone(zoneId).toEpochSecond();
            return getStatements(epochTimeFrom, epochTimeTo).stream().findFirst().orElseThrow().getId();
        }
    }
}
