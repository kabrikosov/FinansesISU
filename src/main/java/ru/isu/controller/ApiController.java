package ru.isu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.isu.model.Product;
import ru.isu.model.Subscription;
import ru.isu.model.User;
import ru.isu.projection.GrouppingSumProjection;
import ru.isu.projection.ProductAndSum;
import ru.isu.projection.ProductSumGroupCategories;
import ru.isu.repository.UserRepository;
import ru.isu.service.ProductStatisticsService;
import ru.isu.service.SubscriptionStatisticsService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ProductStatisticsService productStatisticsService;

    @Autowired
    private SubscriptionStatisticsService subscriptionStatisticsService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/statistics/search")
    public List<ProductAndSum> searchBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        var user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return productStatisticsService.findProductsByDateBetween(start, end, user);
    }

    @GetMapping("/statistics/sum")
    public Integer searchSumBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @AuthenticationPrincipal User user
    ){
        return productStatisticsService.findSumByDateBetween(start, end, user);
    }



    @GetMapping("/statistics/sumGroup")
    public List<ProductSumGroupCategories> searchBetweenLocalDateGroupByCategory(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @AuthenticationPrincipal User user
    ){
        return productStatisticsService.findSumByDateBetweenGroupByCategory(start, end, user);
    }

    @GetMapping("/statistics/subscriptionsSum")
    public Integer searchSubscriptionsSumBetweenDates(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @AuthenticationPrincipal User user
    ){
        return subscriptionStatisticsService.searchSubscriptionsSumBetweenDates(start, end, user);
    }

    @GetMapping("/statistics/subscriptions")
    public List<Subscription> searchSubsBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @AuthenticationPrincipal User user
    ){
        return subscriptionStatisticsService.findSubscriptionsByDates(start, end, user);
    }

    @GetMapping("/statistics/subscriptionsGroupping")
    public List<GrouppingSumProjection> searchSubscriptionsSumGroupping(@RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                                                                        @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                                                                        @AuthenticationPrincipal User user
    ){
        return subscriptionStatisticsService.findSubscriptionsSumGroupping(start, end, user);
    }

    @GetMapping("/statistics")
    public List<Product> index(@AuthenticationPrincipal User user){

        return productStatisticsService.findAllByUser(user);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public User register(@RequestBody User user) {
        var ret = userRepository.save(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ret;
    }
}
