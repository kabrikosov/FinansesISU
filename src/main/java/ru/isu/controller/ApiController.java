package ru.isu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.isu.model.Product;
import ru.isu.model.Subscription;
import ru.isu.projection.GrouppingSumProjection;
import ru.isu.projection.ProductAndSum;
import ru.isu.projection.ProductSumGroupCategories;
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

    @GetMapping("/statistics/search")
    public List<ProductAndSum> searchBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return productStatisticsService.findProductsByDateBetween(start, end);
    }

    @GetMapping("/statistics/sum")
    public Integer searchSumBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return productStatisticsService.findSumByDateBetween(start, end);
    }



    @GetMapping("/statistics/sumGroup")
    public List<ProductSumGroupCategories> searchBetweenLocalDateGroupByCategory(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return productStatisticsService.findSumByDateBetweenGroupByCategory(start, end);
    }

    @GetMapping("/statistics/subscriptionsSum")
    public Integer searchSubscriptionsSumBetweenDates(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return subscriptionStatisticsService.searchSubscriptionsSumBetweenDates(start, end);
    }

    @GetMapping("/statistics/subscriptions")
    public List<Subscription> searchSubsBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return subscriptionStatisticsService.findSubscriptionsByDates(start, end);
    }

    @GetMapping("/statistics/subscriptionsGroupping")
    public List<GrouppingSumProjection> searchSubscriptionsSumGroupping(@RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                                                                        @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return subscriptionStatisticsService.findSubscriptionsSumGroupping(start, end);
    }

    @GetMapping("/statistics")
    public List<Product> index(){
        return productStatisticsService.findAll();
    }
}
