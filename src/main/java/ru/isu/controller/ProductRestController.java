package ru.isu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.isu.model.Product;
import ru.isu.repository.CategoryRepository;
import ru.isu.repository.ProductRepository;
import ru.isu.validator.ProductValidator;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(new ProductValidator());
    }

    @GetMapping(value = {"/all", ""})
    public List<Product> viewAll(){
        return repository.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        return new ResponseEntity<Product>(repository.save(product), HttpStatus.OK);
    }

    @GetMapping("/{product}")
    public Product view(@PathVariable Product product){
        return product;
    }

}
