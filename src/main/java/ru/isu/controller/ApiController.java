package ru.isu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.isu.model.Product;
import ru.isu.projection.ProductAndSum;
import ru.isu.projection.ProductSumGroupCategories;
import ru.isu.projection.SubscriptionInfo;
import ru.isu.service.ProductStatisticsService;
import ru.isu.service.SubscriptionStatisticsService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ProductStatisticsService productStatisticsService;

    @Autowired
    private SubscriptionStatisticsService subscriptionStatisticsService;

    @GetMapping("/statistics/search")
    public @ResponseBody List<ProductAndSum> searchBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return productStatisticsService.findProductsByDateBetween(start, end);
    }

    @GetMapping("/statistics/sum")
    public @ResponseBody Integer searchSumBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return productStatisticsService.findSumByDateBetween(start, end);
    }



    @GetMapping("/statistics/sumGroup")
    public @ResponseBody
    List<ProductSumGroupCategories> searchBetweenLocalDateGroupByCategory(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return productStatisticsService.findSumByDateBetweenGroupByCategory(start, end);
    }

    @GetMapping("/statistics/subscriptionsSum")
    public @ResponseBody Integer searchSubscriptionsSumBetweenDates(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return subscriptionStatisticsService.searchSubscriptionsSumBetweenDates(start, end);
    }

    @GetMapping("/statistics/subscriptions")
    public @ResponseBody
    List<SubscriptionInfo> searchSubsBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return subscriptionStatisticsService.findSubscriptionsByDates(start, end);
    }

    @GetMapping("/statistics")
    public @ResponseBody List<Product> index(){
        return productStatisticsService.findAll();
    }
}
