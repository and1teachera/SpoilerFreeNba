package com.zlatenov.spoilerfreeapp.model.validator;

import com.zlatenov.spoilerfreeapp.model.binding.UserEditBindingModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Angel Zlatenov
 */
@Component
public class UserEditValidator implements Validator {

    public boolean supports(Class clazz) {
        return UserEditBindingModel.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "field.required");
    }
}
