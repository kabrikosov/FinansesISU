package ru.isu.projection;

import ru.isu.model.User;

import java.time.LocalDateTime;

public interface ProductInfo {
    Integer getId();

    String getName();

    Integer getPrice();

    Integer getQuantity();

    LocalDateTime getDate();

    CategoryInfo getCategory();

    SubscriptionInfo getSubscription();

    UserInfo getUser();

    interface CategoryInfo {
        Integer getId();

        String getName();
    }

    interface SubscriptionInfo {
        Integer getId();

        String getName();

        LocalDateTime getStartDateTime();

        LocalDateTime getExpirationDateTime();

        User getUser();
    }

    interface UserInfo {
        Integer getId();
    }
}
