package com.yourfounds.dao.impl;

import com.yourfounds.dao.CategoryDao;
import com.yourfounds.entity.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Category> getAll() {
        //get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        //create a query
        Query<Category> query = currentSession.createQuery("FROM Category", Category.class);

        //execute query and get a result list
        List<Category> categories = query.getResultList();

        //return the results
        return categories;
    }

    @Override
    public void save(Category category) {
        //get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        //save new category
        currentSession.save(category);
    }

    @Override
    public Category get(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Category category = currentSession.get(Category.class, id);
        return category;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Category category = session.get(Category.class, id);
        session.delete(category);
    }

    @Override
    public void update(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(category);
    }

    @Override
    public boolean isCategoryNameExist(Category category) {
        return getAll().stream()
                .filter(a -> a.getName().equalsIgnoreCase(category.getName()))
                .count() > 0;
    }

    @Override
    public boolean isCategoryHaveRelations(int id) {
        Session session = sessionFactory.getCurrentSession();
        Category category = get(id);
        Query query = session.createQuery("FROM Expense E WHERE E.category = :obj");
        query.setParameter("obj", category);
        return !query.list().isEmpty();
    }

    @Override
    public int replaceCategoryInAllExpenses(int fromCategoryId, int toCategoryId){
        Session session = sessionFactory.getCurrentSession();

        Category fromCategory = get(fromCategoryId);
        Category toCategory = get(toCategoryId);

        Query query = session.createQuery("UPDATE Expense SET category = :toCategory" +
                " WHERE category = :fromCategory");
        query.setParameter("toCategory", toCategory);
        query.setParameter("fromCategory", fromCategory);
        return query.executeUpdate();
    }
}
