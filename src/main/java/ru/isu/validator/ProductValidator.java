package ru.isu.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.isu.model.Product;

@Component
public class ProductValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "price", "error.price.empty");
        ValidationUtils.rejectIfEmpty(errors, "date", "error.date.empty");
        var p = (Product) target;
        if (p.getPrice() != null && p.getPrice() < 0)
            errors.rejectValue("price", "error.price.negative");
        if (p.getCategory() == null)
            errors.rejectValue("category", "error.category.null");
        if (p.getQuantity() != null && p.getQuantity() <= 0)
            errors.rejectValue("quantity", "error.quantity.lessOrEqualsZero");
    }
}
