package com.zlatenov.userauthorisationservice.service;

import com.zlatenov.spoilerfreesportsapi.model.dto.user.UserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.LoggedUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.RegisterUserDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import com.zlatenov.spoilerfreesportsapi.model.exception.CannotRegisterUserException;

/**
 * @author Angel Zlatenov
 */

public interface UserAuthenticationService {

    LoggedUserDto logUser(UserDto authenticateUserDto) throws AuthorisationException;

    LoggedUserDto registerUser(RegisterUserDto registerUserDto) throws CannotRegisterUserException;

}
