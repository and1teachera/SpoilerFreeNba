package com.zlatenov.userauthorisationservice.controller;

import com.zlatenov.spoilerfreesportsapi.enums.Role;
import com.zlatenov.spoilerfreesportsapi.model.dto.AuthenticateUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.LoggedUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.RegisterUserDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import com.zlatenov.spoilerfreesportsapi.model.exception.CannotRegisterUserException;
import com.zlatenov.userauthorisationservice.service.UserAuthenticationServiceImpl;
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

    private final UserAuthenticationServiceImpl userService;

    @PostMapping(path = "/login")
    private ResponseEntity login(@RequestBody AuthenticateUserDto authenticateUserDto) {
        LoggedUserDto loggedUserDto;
        try {
            loggedUserDto = userService.logUser(authenticateUserDto);
        }
        catch (AuthorisationException e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity
                .ok(loggedUserDto);
    }

    @PostMapping(path = "/register")
    private ResponseEntity register(@RequestBody RegisterUserDto registerUserDto) {
        LoggedUserDto loggedUserDto;
        try {
            registerUserDto.setRole(Role.ADMIN);
            loggedUserDto = userService.registerUser(registerUserDto);
        }
        catch (CannotRegisterUserException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity
                .ok(loggedUserDto);
    }
}
