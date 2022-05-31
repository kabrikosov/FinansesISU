package ru.isu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isu.model.Subscription;
import ru.isu.model.User;
import ru.isu.projection.GrouppingSumProjection;
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

    public Integer searchSubscriptionsSumBetweenDates(LocalDate start, LocalDate end, User user){
        return subscriptionRepository.findSumByDateBetween(
                LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX), user);
    }

    public List<Subscription> findSubscriptionsByDates(LocalDate start, LocalDate end, User user){
        return subscriptionRepository.findSubscriptionsByDates(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX), user);
    }

    public List<GrouppingSumProjection> findSubscriptionsSumGroupping(LocalDate start, LocalDate end, User user){
        return subscriptionRepository.findSubscriptionsSumGroupping(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX), user);
    }
    /*
    public List<SubscriptionInfo> findSubscriptionsByStartDateTimeBeforeAndAndExpirationDateTimeAfter(LocalDate start, LocalDate end){
        return subscriptionRepository.findSubscriptionsByStartDateTimeBeforeAndAndExpirationDateTimeAfterOrStartDateTimeBeforeAndExpirationDateTimeIsNull(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
    }
    */
}
