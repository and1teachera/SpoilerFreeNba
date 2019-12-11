package com.zlatenov.userauthorisationservice.service;

import com.zlatenov.spoilerfreesportsapi.enums.Role;
import com.zlatenov.spoilerfreesportsapi.model.dto.AuthenticateUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.LoggedUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.RegisterUserDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import com.zlatenov.spoilerfreesportsapi.model.exception.CannotRegisterUserException;
import com.zlatenov.userauthorisationservice.model.UserEntity;
import com.zlatenov.userauthorisationservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author Angel Zlatenov
 */
@Service
@AllArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserRepository userRepository;

    private static final String EMAIL_REGEX = "^(.+)@(.+)$";

    @Override
    public LoggedUserDto logUser(AuthenticateUserDto authenticateUserDto) throws AuthorisationException {
        UserEntity userEntity = Optional.ofNullable(Pattern.matches(EMAIL_REGEX, authenticateUserDto.getUsername()) ?
                                                            userRepository.findByEmailAndPassword(
                                                                    authenticateUserDto.getUsername(),
                                                                    authenticateUserDto.getPassword()) :
                                                            userRepository.findByUsernameAndPassword(
                                                                    authenticateUserDto.getUsername(),
                                                                    authenticateUserDto.getPassword()))
                .orElseThrow(AuthorisationException::new);

        userEntity.setRole(Role.ADMIN);

        return LoggedUserDto.builder().lastLoginDate(userEntity.getLastLogin()).role(userEntity.getRole()).build();
    }

    @Override
    public LoggedUserDto registerUser(RegisterUserDto registerUserDto) throws CannotRegisterUserException {
        if (userRepository.existsByUsername(registerUserDto.getUsername())) {
            throw new CannotRegisterUserException("User with that username already exists");
        }
        if (userRepository.existsByEmail(registerUserDto.getEmail())) {
            throw new CannotRegisterUserException("User with that email already exists");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerUserDto.getUsername());
        userEntity.setPassword(registerUserDto.getPassword());
        userEntity.setEmail(registerUserDto.getEmail());
        userEntity.setRole(Role.ADMIN);
        userEntity = userRepository.save(userEntity);

        return LoggedUserDto.builder().role(userEntity.getRole()).build();
    }
}
