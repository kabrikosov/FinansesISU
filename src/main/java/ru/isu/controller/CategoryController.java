package ru.isu.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.isu.model.Category;
import ru.isu.repository.CategoryRepository;
import ru.isu.validator.CategoryValidator;
import ru.isu.validator.ProductValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(new CategoryValidator());
    }

    @GetMapping(value = {"/all", ""})
    public ModelAndView viewAll(){
        var mv = new ModelAndView("all_categories");
        mv.addObject("categoryList", repository.findAll());
        return mv;
    }

    @GetMapping("/{category}")
    public ModelAndView view(@PathVariable Category category){
        var mv = new ModelAndView("category");
        mv.addObject("category", category);
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        var mv = new ModelAndView("add_category");
        mv.addObject("category", new Category());
        mv.addObject("categories", repository.findAll());
        return mv;
    }

    @PostMapping(value="/add")
    public String saveCategory(
            @Valid @ModelAttribute("category") Category category,
            Errors errors,
            Model model,
            RedirectAttributes attr){
        if (errors.hasErrors()){
            model.addAttribute("category", category);
            return "add_category";
        }else{
            repository.save(category);
            attr.addFlashAttribute("category", category);
            return "redirect:/categories/category";
        }
    }

    @RequestMapping("/category")
    public String showNewCategory(
            @ModelAttribute("category") Category c,
            Model model) throws Exception {
        if (c.getId()!=null){
            model.addAttribute("category", c);
            return "category";
        }else{
            throw new Exception("Error category id");
        }
    }
}
