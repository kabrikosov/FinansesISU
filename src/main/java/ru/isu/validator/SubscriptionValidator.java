package ru.isu.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.isu.model.Subscription;

@Component
public class SubscriptionValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Subscription.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var s = (Subscription) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name.empty");
        if (s.getStartDateTime() == null)
            errors.rejectValue("startDateTime", "error.startDateTime.empty");
        if (s.getExpirationDateTime().isBefore(s.getStartDateTime())){
            errors.rejectValue("expirationDateTime", "error.expirationDateTime.beforeStart");
        }
    }
}
