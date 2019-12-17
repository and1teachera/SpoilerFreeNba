package com.zlatenov.spoilerfreeapp.transformer;

import com.zlatenov.spoilerfreeapp.model.binding.UserEditBindingModel;
import com.zlatenov.spoilerfreeapp.model.binding.UserRoleBindingModel;
import com.zlatenov.spoilerfreeapp.model.service.UserServiceModel;
import com.zlatenov.spoilerfreeapp.model.view.UserProfileViewModel;
import org.springframework.stereotype.Component;

/**
 * @author Angel Zlatenov
 */

@Component
public class UserModelTransformer {
    public UserProfileViewModel transformUserToViewModel(UserServiceModel userServiceModel) {
        return null;
    }

    public UserServiceModel transformToServiceModel(UserEditBindingModel userBindingModel) {
        return null;
    }

    public UserServiceModel transformToServiceModel(UserRoleBindingModel userRoleBindingModel) {
        return null;
    }
}
