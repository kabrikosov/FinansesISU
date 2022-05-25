package ru.isu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isu.model.Product;
import ru.isu.model.User;
import ru.isu.projection.ProductAndSum;
import ru.isu.projection.ProductSumGroupCategories;
import ru.isu.repository.CategoryRepository;
import ru.isu.repository.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service("productStatisticsService")
public class ProductStatisticsService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<ProductAndSum> findProductsByDateBetween(LocalDate start, LocalDate end, User user){
        return productRepository.findProductsByDateBetweenAndUserEquals(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX), user);
    }

    public Integer findSumByDateBetween(LocalDate start, LocalDate end, User user){
        return productRepository.findSumByDateBetween(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX), user);
    }

    public List<ProductSumGroupCategories> findSumByDateBetweenGroupByCategory(LocalDate start, LocalDate end, User user){
        return productRepository.findSumByDateBetweenGroupByCategory(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX), user);
    }
    public List<Product> findAllByUser(User user){
        return productRepository.findAllByUser(user);
    }

}
