package ru.isu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.isu.model.Subscription;
import ru.isu.repository.SubscriptionRepository;
import ru.isu.validator.SubscriptionValidator;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionRestController {

    @Autowired
    private SubscriptionRepository repository;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(new SubscriptionValidator());
    }

    @GetMapping(value = {"/all", ""})
    public @ResponseBody List<Subscription> viewAll(){
        return repository.findAll();
    }

    @GetMapping("/{subscription}")
    public @ResponseBody Subscription view(@PathVariable Subscription subscription){
        return subscription;
    }
}
