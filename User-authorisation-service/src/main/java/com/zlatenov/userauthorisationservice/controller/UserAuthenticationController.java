package com.zlatenov.userauthorisationservice.controller;

import com.zlatenov.spoilerfreesportsapi.model.dto.user.LogUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.RegisterUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.UserDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import com.zlatenov.spoilerfreesportsapi.model.exception.CannotRegisterUserException;
import com.zlatenov.userauthorisationservice.service.UserAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Angel Zlatenov
 */

@RestController
@AllArgsConstructor
public class UserAuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    @PostMapping(path = "/login")
    private ResponseEntity login(@RequestBody LogUserDto authenticateUserDto) {
        UserDto loggedUserDto;
        try {
            loggedUserDto = userAuthenticationService.logUser(authenticateUserDto);
        }
        catch (AuthorisationException e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity
                .ok(loggedUserDto);
    }

    @PostMapping(path = "/register")
    private ResponseEntity register(@RequestBody RegisterUserDto registerUserDto) {
        UserDto loggedUserDto;
        try {
            //registerUserDto.setRole(Role.valueOf(""));
            loggedUserDto = userAuthenticationService.registerUser(registerUserDto);
        }
        catch (CannotRegisterUserException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity
                .ok(loggedUserDto);
    }
}
