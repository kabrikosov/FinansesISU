package ru.isu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.isu.model.Category;
import ru.isu.repository.CategoryRepository;
import ru.isu.validator.CategoryValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    @Autowired
    private CategoryRepository repository;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(new CategoryValidator());
    }

    @GetMapping(value = {"/all", ""})
    public @ResponseBody List<Category> viewAll(){
        return repository.findAll();
    }

}
