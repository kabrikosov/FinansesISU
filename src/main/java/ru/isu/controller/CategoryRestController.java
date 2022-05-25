package ru.isu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.isu.model.Category;
import ru.isu.model.Product;
import ru.isu.model.User;
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
    public List<Category> viewAll(){
        return repository.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Category> addCategory(@RequestBody Category category, @AuthenticationPrincipal User user){
        return new ResponseEntity<Category>(repository.save(category), HttpStatus.OK);
    }
    @PutMapping("/put")
    public Category updateCategory(@RequestBody Category category) {
        return repository.save(category);
    }

    @DeleteMapping("/{category}/delete")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Category category){
        repository.delete(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
