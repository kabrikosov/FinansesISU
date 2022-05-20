package ru.isu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isu.projection.SubscriptionInfo;
import ru.isu.repository.ProductRepository;
import ru.isu.repository.SubscriptionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service("subscriptionStatisticsService")
public class SubscriptionStatisticsService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ProductRepository productRepository;

    public Integer searchSubscriptionsSumBetweenDates(LocalDate start, LocalDate end){
        return subscriptionRepository.findSumByDateBetween(
                LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
    }

    public List<SubscriptionInfo> findSubscriptionsByDates(LocalDate start, LocalDate end){
        return subscriptionRepository.findSubscriptionsByDates(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
    }

    /*
    public List<SubscriptionInfo> findSubscriptionsByStartDateTimeBeforeAndAndExpirationDateTimeAfter(LocalDate start, LocalDate end){
        return subscriptionRepository.findSubscriptionsByStartDateTimeBeforeAndAndExpirationDateTimeAfterOrStartDateTimeBeforeAndExpirationDateTimeIsNull(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
    }
    */
}
