package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.binding.LoginFormBindingModel;
import com.zlatenov.spoilerfreeapp.model.binding.RegisterFormBindingModel;
import com.zlatenov.spoilerfreeapp.model.service.UserServiceModel;
import com.zlatenov.spoilerfreeapp.model.binding.UserRoleBindingModel;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;

import java.util.List;

/**
 * @author Angel Zlatenov
 */
public interface UserService {

    void logUser(LoginFormBindingModel loginForm) throws AuthorisationException;

    void registerUser(RegisterFormBindingModel registerForm) throws AuthorisationException;

    UserServiceModel findUserByUserName(String name);

    void editUserProfile(UserServiceModel user);

    List<UserRoleBindingModel> getUserRoleViewModels();

    void changeRole(UserServiceModel transformToServiceModel);
}
