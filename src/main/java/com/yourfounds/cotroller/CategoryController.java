package com.yourfounds.cotroller;

import com.yourfounds.entity.Category;
import com.yourfounds.entity.User;
import com.yourfounds.service.CategoryService;
import com.yourfounds.service.UserService;
import com.yourfounds.util.SecurityUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("allexpense")
    public String listExpenseCategories (Model model){
        List<Category> categories = categoryService.getAllExpenseCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("categoryType", "expense");
        return "category/all";
    }

    @GetMapping("allincome")
    public String listIncomeCategories (Model model){
        List<Category> categories = categoryService.getAllIncomeCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("categoryType", "income");
        return "category/all";
    }

    @RequestMapping("add")
    public String addCategory(Model model){
        Category category = new Category();

        model.addAttribute("category", category);
        return "category/add";
    }

    @RequestMapping("addProcess")
    public String processAddCategoryForm(@ModelAttribute("category") Category category, Model model){
        if (categoryService.isCategoryNameExist(category)){
            model.addAttribute("category", category);
            return "category/nameexist";
        }
        User user = userService.getUser(SecurityUserHandler.getCurrentUser());
        category.setUser(user);
        categoryService.addCategory(category);
        return "success";
    }

    @RequestMapping("delete")
    public String deleteCategory(@ModelAttribute("categoryId") int categoryId, Model model){
        //check if there is relationship on category
        if (categoryService.isCategoryHaveRelations(categoryId)){
            //inform User that category can't be deleted
            //and propose to create new category
            //or transfer expenses to exist category
            Category categoryToDelete = categoryService.getCategory(categoryId);
            List<Category> categories = categoryService.getAllExpenseCategories();
            categories.remove(categoryToDelete);

            Category category = new Category();
            model.addAttribute("categoryToDelete", categoryToDelete);
            model.addAttribute("categories", categories);
            model.addAttribute("category", category);
            return "category/cantdelete";
        } else {
            categoryService.deleteCategoryById(categoryId);
            return "success";
        }
    }

    @GetMapping("update")
    public String updateCategory(@ModelAttribute("categoryId") int categoryId, Model model){
        Category category = categoryService.getCategory(categoryId);
        model.addAttribute("category", category);
        return "category/update";
    }

    @RequestMapping("updateprocess")
    public String updateProcess(@ModelAttribute("category") Category category){
        //when update category User for some reason null
        //todo: rework this
        category.setUser(userService.getUser(SecurityUserHandler.getCurrentUser()));
        categoryService.updateCategory(category);
        return "redirect:all";
    }

    @RequestMapping("transferToExistCategory")
    public String transferToOtherCategoryAndDelete(@ModelAttribute("categoryId") int toCategoryId, @ModelAttribute("categoryToDelete") int fromCategoryId, Model model){
        categoryService.replaceCategoryInAllExpenses(fromCategoryId, toCategoryId);
        return "success";
    }

    @RequestMapping("transferToNewCategory")
    public String transferToNewCategoryAndDelete(@ModelAttribute("newCategory") Category category, @ModelAttribute("categoryToDelete") int fromCategoryId, Model model){
        String username = SecurityUserHandler.getCurrentUser();
        category.setUser(userService.getUser(username));
        categoryService.addCategory(category);
        categoryService.replaceCategoryInAllExpenses(fromCategoryId, category.getCategoryId());
        return "success";
    }
}
