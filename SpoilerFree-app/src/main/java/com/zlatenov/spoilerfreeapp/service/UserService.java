package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.form.LoginForm;
import com.zlatenov.spoilerfreeapp.model.form.RegisterForm;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;

/**
 * @author Angel Zlatenov
 */
public interface UserService {

    void logUser(LoginForm loginForm) throws AuthorisationException;

    void registerUser(RegisterForm registerForm) throws AuthorisationException;
}
