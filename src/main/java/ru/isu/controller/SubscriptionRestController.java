package ru.isu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.isu.model.Product;
import ru.isu.model.Subscription;
import ru.isu.model.User;
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
    public List<Subscription> viewAll(@AuthenticationPrincipal User user){
        return repository.findAllByUser(user);
    }

    @PutMapping("/{oldSubscription}/put")
    public Subscription updateSubscription(@RequestBody Subscription newSubscription, @PathVariable Subscription oldSubscription) {
        oldSubscription.setName(newSubscription.getName());
        oldSubscription.setExpirationDateTime(newSubscription.getExpirationDateTime());
        oldSubscription.setStartDateTime(newSubscription.getStartDateTime());
        return repository.save(oldSubscription);
    }

    @DeleteMapping("/{subscription}/delete")
    public ResponseEntity<HttpStatus> deleteSubscription(@PathVariable Subscription subscription){
        repository.delete(subscription);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{subscription}")
    public Subscription view(@PathVariable Subscription subscription){
        return subscription;
    }
}
