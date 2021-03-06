package ru.isu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.isu.model.Product;
import ru.isu.model.Subscription;
import ru.isu.model.User;
import ru.isu.repository.SubscriptionRepository;
import ru.isu.validator.ProductValidator;
import ru.isu.validator.SubscriptionValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository repository;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(new SubscriptionValidator());
    }

    @GetMapping(value = {"/all", ""})
    public ModelAndView viewAll(){
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var mv = new ModelAndView("all_subscriptions");
        mv.addObject("subscriptionList", repository.findAllByUser(user));
        return mv;
    }

    @GetMapping("/{subscription}")
    public ModelAndView view(@PathVariable Subscription subscription){
        var mv = new ModelAndView("subscription");
        mv.addObject("subscription", subscription);
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var mv = new ModelAndView("add_subscription");
        var sub = new Subscription();
        sub.setUser(user);
        mv.addObject("subscription", sub);
        return mv;
    }

    @PostMapping(value="/add")
    public String saveSubscription(
            @Valid @ModelAttribute("subscription") Subscription subscription,
            Errors errors,
            Model model,
            RedirectAttributes attr,
            @AuthenticationPrincipal User user){
        if (errors.hasErrors()){
            subscription.setUser(user);
            model.addAttribute("subscription", subscription);
            return "add_subscription";
        }else{
            subscription.setUser(user);
            repository.save(subscription);
            attr.addFlashAttribute("subscription", subscription);
            return "redirect:/subscriptions/subscription";
        }
    }

    @RequestMapping("/subscription")
    public String showNewSubscription(
            @ModelAttribute("subscription") Subscription s,
            Model model) throws Exception {
        if (s.getId()!=null){
//            model.addAttribute("subscription", s);
            return "redirect:/subscriptions";
        }else{
            throw new Exception("Error subscription id");
        }
    }
}
