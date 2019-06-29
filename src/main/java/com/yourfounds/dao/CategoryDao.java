package com.yourfounds.dao;

import com.yourfounds.entity.Category;

public interface CategoryDao extends CrudDao<Category,Integer> {

    boolean isCategoryNameExist(Category category);
    boolean isCategoryHaveRelations(Integer id);
    int replaceCategoryInAllExpenses(Integer fromCategoryId, Integer toCategoryId);

}