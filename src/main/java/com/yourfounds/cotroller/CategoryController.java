package com.yourfounds.cotroller;

import com.yourfounds.entity.Category;
import com.yourfounds.entity.User;
import com.yourfounds.service.CategoryService;
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
    private CategoryService categoryService;

    @GetMapping("all")
    public String listCategories (Model model){
        // get categories from DAO
        List<Category> categories = categoryService.getCategories();

        // add customers to the model
        model.addAttribute("categories", categories);
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
        //todo: User is hardcoded. Replace it when implement needed logic.
        User user = new User();
        user.setUsername("user1");
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
            List<Category> categories = categoryService.getCategories();
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
        categoryService.addCategory(category);
        categoryService.replaceCategoryInAllExpenses(fromCategoryId, category.getCategoryId());
        return "success";
    }
}
