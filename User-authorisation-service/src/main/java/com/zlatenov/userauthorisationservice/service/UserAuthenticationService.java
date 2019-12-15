package com.zlatenov.userauthorisationservice.service;

import com.zlatenov.spoilerfreesportsapi.model.dto.user.LogUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.UserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.RegisterUserDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import com.zlatenov.spoilerfreesportsapi.model.exception.CannotRegisterUserException;

/**
 * @author Angel Zlatenov
 */

public interface UserAuthenticationService {

    UserDto logUser(LogUserDto authenticateUserDto) throws AuthorisationException;

    UserDto registerUser(RegisterUserDto registerUserDto) throws CannotRegisterUserException;

}
