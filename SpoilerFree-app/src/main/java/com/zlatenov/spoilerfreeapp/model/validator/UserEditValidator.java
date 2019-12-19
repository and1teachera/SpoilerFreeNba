package com.zlatenov.spoilerfreeapp.model.validator;

import com.zlatenov.spoilerfreeapp.model.binding.UserEditBindingModel;
import org.apache.commons.lang3.StringUtils;
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
        if (!StringUtils.equals(((UserEditBindingModel) target).getPassword(), ((UserEditBindingModel) target).getConfirmPassword())) {
            errors.reject("fields.not.match");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required");
    }
}
