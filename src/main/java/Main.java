import entity.Account;
import entity.Category;
import entity.Expense;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        User user = new User();
        user.setName("Name");
        user.setSurname("Surname");
        user.setEmail("firstUser@gmail.com");
        user.setPassword("pass");

        Category category = new Category();
        category.setName("First");
        category.setDescription("First description");

        Expense expense = new Expense();
        expense.setUserId(1);
        expense.setCategory(1);
        expense.setComment("First comment");
        expense.setDate(LocalDate.now());
        expense.setTime(LocalTime.now());
        expense.setSum(114.50);

        Account account = new Account();
        account.setUserId(1);
        account.setDescription("First description");
        account.setSummary(1000.25);
        account.setType("Credit");



        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();
        session.save(user);
        session.save(category);
        session.save(expense);
        session.save(account);
        transaction.commit();

        session.close();
        sessionFactory.close();
    }
}