package com.zlatenov.userauthorisationservice.service;

import com.zlatenov.nospoilersportsapi.model.dto.AuthenticateUserDto;
import com.zlatenov.nospoilersportsapi.model.dto.LoggedUserDto;
import com.zlatenov.nospoilersportsapi.model.dto.RegisterUserDto;
import com.zlatenov.nospoilersportsapi.model.exception.AuthorisationException;
import com.zlatenov.nospoilersportsapi.model.exception.CannotRegisterUserException;

/**
 * @author Angel Zlatenov
 */

public interface UserAuthenticationService {

        LoggedUserDto logUser(AuthenticateUserDto authenticateUserDto) throws AuthorisationException;

        LoggedUserDto registerUser(RegisterUserDto registerUserDto) throws CannotRegisterUserException;

}
