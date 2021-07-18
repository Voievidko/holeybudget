package com.holeybudget.dao.impl;

import com.holeybudget.dao.AccountDao;
import com.holeybudget.entity.Account;
import com.holeybudget.util.SecurityUserHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {



    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Account get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Account.class, id);
    }

    @Override
    public List<Account> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Account> query = session.createQuery("from Account where username = :param", Account.class);
        query.setParameter("param", SecurityUserHandler.getCurrentUser());
        return query.getResultList();
    }

    @Override
    public void save(Account account) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(account);
    }

    @Override
    public void update(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(account);
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Account account = session.get(Account.class, id);
        session.delete(account);
    }

    @Override
    public boolean isAccountHaveRelations(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Account account = get(id);
        Query query = session.createQuery("FROM Expense E WHERE E.account = :obj");
        query.setParameter("obj", account);
        return !query.list().isEmpty();
    }

    @Override
    public int replaceAccountInAllExpenses(Integer fromAccountId, Integer toAccountId){
        Session session = sessionFactory.getCurrentSession();

        Account fromAccount = get(fromAccountId);
        Account toAccount = get(toAccountId);

        Query query = session.createQuery("UPDATE Expense SET account = :toAccount" +
                " WHERE account = :fromAccount");
        query.setParameter("toAccount", toAccount);
        query.setParameter("fromAccount", fromAccount);
        return query.executeUpdate();
    }
}
