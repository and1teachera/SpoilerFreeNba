package com.zlatenov.nospoilerapp.service;

import com.zlatenov.nospoilerapp.model.form.LoginForm;
import com.zlatenov.nospoilerapp.model.form.RegisterForm;
import com.zlatenov.nospoilersportsapi.model.exception.AuthorisationException;

/**
 * @author Angel Zlatenov
 */
public interface UserService {

    void logUser(LoginForm loginForm) throws AuthorisationException;

    void registerUser(RegisterForm registerForm) throws AuthorisationException;
}
