package com.holeybudget.service.impl.sync;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holeybudget.entity.Account;
import com.holeybudget.entity.Category;
import com.holeybudget.entity.Expense;
import com.holeybudget.exception.AccountSyncFailedException;
import com.holeybudget.service.AccountService;
import com.holeybudget.service.CategoryService;
import com.holeybudget.service.ExpenseService;
import com.holeybudget.service.ExpenseSyncService;
import com.holeybudget.service.MccService;
import com.holeybudget.util.TimeHelper;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
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

    private static final int MAX_MONOBANK_STATEMENT_TIME_IN_SECONDS = 2682000; // s (1 month)

    private static final String MONOBANK_API_ENDPOINT = "https://api.monobank.ua/";

    private static final int DEFAULT_CARD_NUMBER = 0;

    private final ExpenseService expenseService;

    private final CategoryService categoryService;

    private final AccountService accountService;

    private final MccService mccService;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ExpenseSyncService expenseSyncService;

    @Autowired
    public MonobankSyncServiceImpl(ExpenseService expenseService, CategoryService categoryService,
                                   AccountService accountService, MccService mccService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.mccService = mccService;
    }

    @Override
    @Transactional
    public void syncDataWithBankServer(List<Account> accounts) throws AccountSyncFailedException {
        if (accounts == null || accounts.isEmpty()){
            log.error("List with accounts are empty or null");
            throw new AccountSyncFailedException("List with accounts are empty or null");
        }

        List<Account> accountWithTokensToSync = accounts.stream()
                .filter(a -> a.getToken() != null && !a.getToken().isEmpty())
                .collect(Collectors.toList());

        if (accountWithTokensToSync.isEmpty()) {
            throw new AccountSyncFailedException("There is no tokens to sync.");
        }

        for (Account account : accountWithTokensToSync){
            Session session = sessionFactory.getCurrentSession();
            Account lockedAccount = session.find(Account.class, account.getAccountId(), LockModeType.PESSIMISTIC_WRITE);
            log.debug("Account with ID: '" + lockedAccount.getAccountId() + "' locked");
            expenseSyncService.syncAccount(lockedAccount);
        }
    }

    @Override
    @Transactional
    public void syncAccount(Account lockedAccount){
        log.debug("Start to sync account with ID: '" + lockedAccount.getAccountId() + "'");

        long delayBetweenSync = TEN_MINUTES + 1;
        if (lockedAccount.getSynchronizationTime() != null) {
            delayBetweenSync = TimeHelper.getCurrentEpochTime() - lockedAccount.getSynchronizationTime();
            log.debug("Delay between sync for account with ID: '" + lockedAccount.getAccountId() +"' is " + delayBetweenSync + " sec");
        }

        if (delayBetweenSync < TEN_MINUTES){
            //if last sync was 10 min ago don't need to sync again
            lockedAccount.setSynchronizationTime(TimeHelper.getCurrentEpochTime());
            accountService.updateAccount(lockedAccount);
            log.debug("Account with ID: '" + lockedAccount.getAccountId() + "' will not sync now because of delay.");
            return;
        }

        if (lockedAccount.getSynchronizationTime() == null){
            //this mean it's first time sync for this account
            //so, we can define last sync id and set current time for last sync
            log.debug("First sync for account: " + lockedAccount.getAccountId());
            String lastTransactionId = null;
            try {
                lastTransactionId = getLastTransactionId(lockedAccount);
            } catch (Exception e) {
                log.warn("Can't get last transaction id" + e);
            }
            lockedAccount.setSynchronizationId(lastTransactionId);
            lockedAccount.setSynchronizationTime(TimeHelper.getCurrentEpochTime());
            accountService.updateAccount(lockedAccount);
            return;
        }

        List<MonobankStatementAnswer> monobankStatementAnswers = null;
        long currentEpochTime = TimeHelper.getCurrentEpochTime();
        long epochTimeFrom = currentEpochTime - MAX_MONOBANK_STATEMENT_TIME_IN_SECONDS;

        String firstSuccessfulSyncId = null;
        String lastSuccessSyncId = lockedAccount.getSynchronizationId();

        try {
            log.debug("Retrieve monobank statements for account with ID: '" + lockedAccount.getAccountId() + "'");
            monobankStatementAnswers = getStatements(lockedAccount, epochTimeFrom, currentEpochTime);
        } catch (Exception e) {
            log.error("Can't retrieve statements.", e);
            return;
        }

        for (MonobankStatementAnswer monobankExpense : monobankStatementAnswers) {
            String currentSyncId = monobankExpense.getId();
            if (currentSyncId.equals(lastSuccessSyncId)) {
                log.debug("Last sync id was found for account ID: '" + lockedAccount.getAccountId() + "'");
                if (firstSuccessfulSyncId != null) {
                    lockedAccount.setSynchronizationId(firstSuccessfulSyncId);
                }
                lockedAccount.setSynchronizationTime(TimeHelper.getCurrentEpochTime());
                accountService.updateAccount(lockedAccount);
                log.debug("Finish to sync account with ID: '" + lockedAccount.getAccountId() + "'");
                log.debug("Account with ID: '" + lockedAccount.getAccountId() + "' unlocked");
                return;
            } else {
                String mccCategoryName = mccService.getCategoryByMccCode(monobankExpense.getMcc());
                if (mccCategoryName.isEmpty()) {
                    continue;
                }
                Expense expense = createExpense(lockedAccount, monobankExpense);
                Category category = findExistingOrCreateNewCategory(mccCategoryName);
                saveCategoryIfItNew(lockedAccount, mccCategoryName, category);
                saveMonobankExpense(monobankExpense, expense, category);

                if (firstSuccessfulSyncId == null) {
                    log.debug("firstSuccessfulSyncId is null, so get it from monobankExpense");
                    firstSuccessfulSyncId = monobankExpense.getId();
                }
            }
        }
        log.warn("Seems there is no sync during last month for account '" + lockedAccount.getAccountId() + "'.");
        if (firstSuccessfulSyncId != null) {
            lockedAccount.setSynchronizationId(firstSuccessfulSyncId);
        }
    }

    private void saveMonobankExpense(MonobankStatementAnswer monobankExpense, Expense expense, Category category) {
        long epochTime = monobankExpense.getTime();
        LocalDate date = LocalDate.ofInstant(Instant.ofEpochSecond(epochTime), ZoneId.systemDefault());
        expense.setCategory(categoryService.getCategory(category.getName()));
        expense.setDate(date);
        expense.setTime(getTimeFromDaySeconds(epochTime));

        expenseService.addExpense(expense);
    }

    private void saveCategoryIfItNew(Account lockedAccount, String mccCategoryName, Category category) {
        if (category.getCategoryId() == 0) {
            category.setUser(lockedAccount.getUser());
            category.setIncome(false);
            category.setName(mccCategoryName);
            categoryService.addCategory(category);
        }
    }

    private Category findExistingOrCreateNewCategory(String mccCategoryName) {
        List<Category> categories = categoryService.getAllExpenseCategories();
        return categories.stream()
                .filter(c -> c.getName().equalsIgnoreCase(mccCategoryName))
                .findFirst()
                .orElse((new Category()));
    }

    private LocalTime getTimeFromDaySeconds(long epochTime) {
        return LocalTime.ofSecondOfDay(epochTime % 3600);
    }

    private Expense createExpense(Account lockedAccount, MonobankStatementAnswer monobankExpense) {
        log.debug("Create new expense for account with ID: '" + lockedAccount.getAccountId() + "'.");
        Expense expense = new Expense();
        expense.setUser(lockedAccount.getUser());
        expense.setAccount(lockedAccount);
        expense.setCurrency(lockedAccount.getCurrency());
        expense.setComment(monobankExpense.getDescription());
        expense.setSum(-(monobankExpense.getAmount() / 100d));
        return expense;
    }

    private List<MonobankStatementAnswer> getStatements(Account account, long timeFrom, long timeTo){
        ObjectMapper mapper = new ObjectMapper();
        List<MonobankStatementAnswer> monobankStatementAnswers;
        try {
            String jsonResponse = getJsonWithStatements(account, timeFrom, timeTo).orElseThrow();
            monobankStatementAnswers = mapper.readValue(jsonResponse, new TypeReference<List<MonobankStatementAnswer>>(){});
        } catch (Exception e) {
            log.error("Can't parse answer." + e);
            return Collections.emptyList();
        }
        return monobankStatementAnswers;
    }

    private Optional<String> getJsonWithStatements(Account account, long timeFrom, long timeTo){
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

    private String getLastTransactionId(Account account){
        LocalDateTime currentTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        long epochTimeTo = currentTime.atZone(zoneId).toEpochSecond();
        long epochTimeFrom = currentTime.minus(30, ChronoUnit.DAYS).atZone(zoneId).toEpochSecond();
        return getStatements(account, epochTimeFrom, epochTimeTo).stream().findFirst().orElseThrow().getId();
    }
}
