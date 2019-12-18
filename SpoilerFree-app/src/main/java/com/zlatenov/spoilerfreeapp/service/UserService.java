package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.exception.VideoNotAvailableException;
import com.zlatenov.spoilerfreeapp.model.binding.LoginFormBindingModel;
import com.zlatenov.spoilerfreeapp.model.binding.RegisterFormBindingModel;
import com.zlatenov.spoilerfreeapp.model.binding.UserRoleBindingModel;
import com.zlatenov.spoilerfreeapp.model.service.UserServiceModel;
import com.zlatenov.spoilerfreeapp.model.service.VideoServiceModel;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;

import java.util.List;

/**
 * @author Angel Zlatenov
 */
public interface UserService {

    void logUser(LoginFormBindingModel loginForm) throws AuthorisationException;

    void registerUser(RegisterFormBindingModel registerForm) throws AuthorisationException;

    UserServiceModel getUserByUserName(String name) throws AuthorisationException;

    void editUserProfile(UserServiceModel user);

    List<UserRoleBindingModel> getUserRoleViewModels();

    void changeRole(UserServiceModel transformToServiceModel) throws AuthorisationException;

    void addRemoveFromFavorites(VideoServiceModel videoServiceModel, String name) throws AuthorisationException, VideoNotAvailableException;

    List<VideoServiceModel> getFavourites(String username) throws AuthorisationException;
}
