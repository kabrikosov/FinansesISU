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
@RequestMapping("/api/statistics")
public class StatisticsRestController {

    @Autowired
    private ProductStatisticsService productStatisticsService;

    @Autowired
    private SubscriptionStatisticsService subscriptionStatisticsService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/search")
    public List<ProductAndSum> searchBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        var user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return productStatisticsService.findProductsByDateBetween(start, end, user);
    }

    @GetMapping("/sum")
    public Integer searchSumBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @AuthenticationPrincipal User user
    ){
        return productStatisticsService.findSumByDateBetween(start, end, user);
    }



    @GetMapping("/sumGroup")
    public List<ProductSumGroupCategories> searchBetweenLocalDateGroupByCategory(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @AuthenticationPrincipal User user
    ){
        return productStatisticsService.findSumByDateBetweenGroupByCategory(start, end, user);
    }

    @GetMapping("/subscriptionsSum")
    public Integer searchSubscriptionsSumBetweenDates(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @AuthenticationPrincipal User user
    ){
        return subscriptionStatisticsService.searchSubscriptionsSumBetweenDates(start, end, user);
    }

    @GetMapping("/subscriptions")
    public List<Subscription> searchSubsBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @AuthenticationPrincipal User user
    ){
        return subscriptionStatisticsService.findSubscriptionsByDates(start, end, user);
    }

    @GetMapping("/subscriptionsGroupping")
    public List<GrouppingSumProjection> searchSubscriptionsSumGroupping(@RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                                                                        @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                                                                        @AuthenticationPrincipal User user
    ){
        return subscriptionStatisticsService.findSubscriptionsSumGroupping(start, end, user);
    }

    @GetMapping("/")
    public List<Product> index(@AuthenticationPrincipal User user){

        return productStatisticsService.findAllByUser(user);
    }
}
