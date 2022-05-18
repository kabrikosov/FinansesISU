package ru.isu.projection;

public interface ProductSumGroupCategories {
    Integer getSum();

    CategoryInfo getCategory();

    public interface CategoryInfo{
        Integer getId();

        String getName();
    }
}
