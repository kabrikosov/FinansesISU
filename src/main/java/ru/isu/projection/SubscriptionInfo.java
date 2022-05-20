package ru.isu.projection;

import ru.isu.model.Category;
import ru.isu.model.Subscription;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriptionInfo {
    String getName();

    LocalDateTime getStartDateTime();

    LocalDateTime getExpirationDateTime();

    List<ProductInfo> getProducts();

    interface ProductInfo {
        Integer getId();

        String getName();

        Integer getPrice();

        Integer getQuantity();

        LocalDateTime getDate();

        Category getCategory();

        Subscription getSubscription();
    }
}
