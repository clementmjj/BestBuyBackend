package com.niit.bestbuyfrontend.contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.niit.bestbuy.dao.CategoryDAO;
import com.niit.bestbuy.model.Category;

@Controller
public class CategoryController 
{
	@Autowired
	CategoryDAO categoryDAO;
	
	@RequestMapping(value="/addCategory", method=RequestMethod.POST)
	public String addCategory(@RequestParam("categoryName") String categoryName, @RequestParam("categoryDesc") String categoryDesc)
	{
		Category category=new Category();
		category.setCategoryName(categoryName);
		category.setCategoryDesc(categoryDesc);
		categoryDAO.add(category);
		return "Category";
	}
	
	@RequestMapping(value="/category")
	public String showCategory(Model m)
	{
		List<Category> categoryList=categoryDAO.listCategories();
		m.addAttribute("categoryList", categoryList); 
		return "Category";
	}
	
	@RequestMapping(value="/deleteCategory/{categoryId}")
	public String deleteCategory(@PathVariable("categoryId")int categoryId,Model m)
	{
		Category category=categoryDAO.getCategory(categoryId);
		categoryDAO.delete(category);
		List<Category> categoryList=categoryDAO.listCategories();
		m.addAttribute("categoryList", categoryList); 
		return "Category";
	}
	
	@RequestMapping(value="/editCategory/{categoryId}")
	public String editCategory(@PathVariable("categoryId")int categoryId,Model m)
	{
		Category category=categoryDAO.getCategory(categoryId);
		m.addAttribute("category",category);
		return "UpdateCategory";
	}
	
	@RequestMapping(value="/updateCategory",method=RequestMethod.POST)
	public String updateCategory(@RequestParam("categoryId")int categoryId,@RequestParam("categoryName")String categoryName,@RequestParam("categoryDesc") String categoryDesc,Model m)
	{
		Category category=categoryDAO.getCategory(categoryId);
		
		category.setCategoryName(categoryName);
		category.setCategoryDesc(categoryDesc);
		
		categoryDAO.update(category);
		
		List<Category> categoryList=categoryDAO.listCategories();
		//binding the categoryList and sending to jsp page
		m.addAttribute("categoryList", categoryList); 
		return "Category";
	}
}