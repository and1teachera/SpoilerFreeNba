package com.zlatenov.userauthorisationservice.service;

import com.zlatenov.spoilerfreesportsapi.model.dto.AuthenticateUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.LoggedUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.RegisterUserDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import com.zlatenov.spoilerfreesportsapi.model.exception.CannotRegisterUserException;

/**
 * @author Angel Zlatenov
 */

public interface UserAuthenticationService {

    LoggedUserDto logUser(AuthenticateUserDto authenticateUserDto) throws AuthorisationException;

    LoggedUserDto registerUser(RegisterUserDto registerUserDto) throws CannotRegisterUserException;

}
