package com.notspend.dao;

import com.notspend.entity.Category;

import java.util.List;

public interface CategoryDao extends CrudDao<Category,Integer> {

    List<Category> getAllIncome();
    boolean isCategoryNameExist(Category category);
    boolean isCategoryHaveRelations(Integer id);
    int replaceCategoryInAllExpenses(Integer fromCategoryId, Integer toCategoryId);
    Category get(String name);

}