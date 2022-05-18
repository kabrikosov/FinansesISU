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
import ru.isu.service.StatisticsService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/statistics/search")
    public @ResponseBody List<ProductAndSum> searchBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return statisticsService.findProductsByDateBetween(start, end);
    }

    @GetMapping("/statistics/sum")
    public @ResponseBody Integer searchSumBetweenLocalDate(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return statisticsService.findSumByDateBetween(start, end);
    }

    @GetMapping("/statistics/sumGroup")
    public @ResponseBody
    List<ProductSumGroupCategories> searchBetweenLocalDateGroupByCategory(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return statisticsService.findSumByDateBetweenGroupByCategory(start, end);
    }

    @GetMapping("/statistics")
    public @ResponseBody List<Product> index(){
        return statisticsService.findAll();
    }
}
