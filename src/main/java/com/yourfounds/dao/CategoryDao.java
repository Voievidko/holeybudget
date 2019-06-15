package com.yourfounds.dao;

import com.yourfounds.entity.Category;

public interface CategoryDao extends CrudDao<Category> {

    boolean isCategoryNameExist(Category category);
    boolean isCategoryHaveRelations(int id);
    int replaceCategoryInAllExpenses(int fromCategoryId, int toCategoryId);

}