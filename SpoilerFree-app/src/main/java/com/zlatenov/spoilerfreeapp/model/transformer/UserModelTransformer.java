package com.zlatenov.spoilerfreeapp.model.transformer;

import com.zlatenov.spoilerfreeapp.model.binding.UserEditBindingModel;
import com.zlatenov.spoilerfreeapp.model.binding.UserRoleBindingModel;
import com.zlatenov.spoilerfreeapp.model.entity.User;
import com.zlatenov.spoilerfreeapp.model.service.UserServiceModel;
import com.zlatenov.spoilerfreeapp.model.view.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Angel Zlatenov
 */
@AllArgsConstructor
@Component
public class UserModelTransformer {

    private final ModelMapper modelMapper;

    private final GamesModelTransformer gamesModelTransformer;

    public UserProfileViewModel transformUserToViewModel(UserServiceModel userServiceModel) {
        return UserProfileViewModel.builder()
                .name(userServiceModel.getName())
                .build();
    }

    public UserServiceModel transformToServiceModel(UserEditBindingModel userBindingModel) {
        return UserServiceModel.builder()
                //.name(userBindingModel.get())
                .build();
    }

    public UserServiceModel transformToServiceModel(UserRoleBindingModel userRoleBindingModel) {
        return UserServiceModel.builder()
                .name(userRoleBindingModel.getUsername())
                .build();
    }

    public UserServiceModel transformToServiceModel(User user) {
        return UserServiceModel.builder()
                .name(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
