package ru.isu.controller;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.isu.model.Product;
import ru.isu.model.User;
import ru.isu.projection.ProductInfo;
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
    public List<Product> viewAll(@AuthenticationPrincipal User user){
        return repository.findAllByUser(user);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public Product addProduct(@RequestBody Product product){
        return repository.save(product);
    }

    @RequestMapping(value = "{oldProduct}/put", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public Product updateProduct(@RequestBody Product newproduct, @PathVariable Product oldProduct) {
        oldProduct.setCategory(newproduct.getCategory());
        oldProduct.setDate(newproduct.getDate());
        oldProduct.setPrice(newproduct.getPrice());
        oldProduct.setQuantity(newproduct.getQuantity());
        oldProduct.setName(newproduct.getName());
        oldProduct.setSubscription(newproduct.getSubscription());
        return repository.save(oldProduct);
    }

    @DeleteMapping("/{product}/delete")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Product product){
        repository.delete(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{product}/detachSubscription")
    public ResponseEntity<HttpStatus> detachSubscription(@PathVariable Product product){
        product.setSubscription(null);
        repository.save(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{product}")
    public ProductInfo view(@PathVariable Product product){
        return repository.getProductById(product.getId());
    }

}
