package ru.isu.projection;

import ru.isu.model.Category;
import ru.isu.model.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductAndSum {
    Integer getId();

    String getName();

    Integer getPrice();

    Integer getQuantity();

    default Integer getTotal(){
        return this.getPrice() * this.getQuantity();
    }

    LocalDateTime getDate();

    CategoryInfo getCategory();

    SubscriptionInfo getSubscription();

    interface CategoryInfo {
        Integer getId();

        String getName();

        Category getParentCategory();

        List<Category> getChildCategories();
    }

    interface SubscriptionInfo {
        Integer getId();

        String getName();

        List<Product> getProducts();

        LocalDateTime getStartDateTime();

        LocalDateTime getExpirationDateTime();
    }
}
