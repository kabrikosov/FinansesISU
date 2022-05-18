package ru.isu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.isu.model.Category;
import ru.isu.model.Product;
import ru.isu.repository.CategoryRepository;
import ru.isu.repository.ProductRepository;
import ru.isu.validator.ProductValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(new ProductValidator());
    }

    @GetMapping(value = {"/all", ""})
    public ModelAndView viewAll(){
        var mv = new ModelAndView("all_products");
        mv.addObject("productList", repository.findAll());
        return mv;
    }

    @GetMapping("/{product}")
    public ModelAndView view(@PathVariable Product product){
        var mv = new ModelAndView("product");
        mv.addObject("product", product);
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        var mv = new ModelAndView("add_product");
        mv.addObject("product", new Product());
        mv.addObject("categories", categoryRepository.findAll());
        return mv;
    }

    @PostMapping(value="/add")
    public String saveProduct(
            @Valid @ModelAttribute("product") Product product,
            Errors errors,
            Model model,
            RedirectAttributes attr){
        if (errors.hasErrors()){
            model.addAttribute("product", product);
            return "add_product";
        } else {
            repository.save(product);
            attr.addFlashAttribute("product", product);
            return "redirect:/products/product";
        }
    }

    @RequestMapping("/product")
    public String showNewProduct(
            @ModelAttribute("product") Product p,
            Model model) throws Exception {
        if (p.getId()!=null){
            model.addAttribute("product", p);
            return "product";
        }else{
            throw new Exception("Error product id");
        }
    }
}
