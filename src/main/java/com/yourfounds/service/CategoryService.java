package com.yourfounds.service;

import com.yourfounds.entity.Category;

import java.util.List;

public interface CategoryService {

    void addCategory(Category category);
    List<Category> getAllExpenseCategories();
    List<Category> getAllIncomeCategories();
    Category getCategory(int id);
    void deleteCategoryById(int id);
    void updateCategory(Category category);

    boolean isCategoryNameExist(Category category);
    boolean isCategoryHaveRelations(int id);
    int replaceCategoryInAllExpenses(int fromCategoryId, int toCategoryId);

}
