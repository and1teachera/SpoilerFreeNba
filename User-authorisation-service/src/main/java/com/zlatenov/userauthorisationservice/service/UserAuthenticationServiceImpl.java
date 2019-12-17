package com.zlatenov.userauthorisationservice.service;

import com.zlatenov.spoilerfreesportsapi.model.dto.user.LogUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.RegisterUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.UserDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import com.zlatenov.spoilerfreesportsapi.model.exception.CannotRegisterUserException;
import com.zlatenov.userauthorisationservice.model.Role;
import com.zlatenov.userauthorisationservice.model.User;
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
    public UserDto logUser(LogUserDto authenticateUserDto) throws AuthorisationException {
        User userEntity = Optional.ofNullable(Pattern.matches(EMAIL_REGEX, authenticateUserDto.getUsername()) ?
                userRepository.findByEmailAndPassword(
                        authenticateUserDto.getUsername(),
                        authenticateUserDto.getPassword()) :
                userRepository.findByUsernameAndPassword(
                        authenticateUserDto.getUsername(),
                        authenticateUserDto.getPassword()))
                .orElseThrow(AuthorisationException::new);

        userEntity.setRole(Role.USER);

        return UserDto.builder().lastLoginDate(userEntity.getLastLogin())
                .role(userEntity.getRole().toString()).build();
    }

    @Override
    public UserDto registerUser(RegisterUserDto registerUserDto) throws CannotRegisterUserException {
        if (userRepository.existsByUsername(registerUserDto.getUsername()) || userRepository.existsByEmail(
                registerUserDto.getEmail())) {
            throw new CannotRegisterUserException("User already exists");
        }
        User userEntity = new User();
        userEntity.setUsername(registerUserDto.getUsername());
        userEntity.setPassword(registerUserDto.getPassword());
        userEntity.setEmail(registerUserDto.getEmail());
        userEntity.setRole(Role.USER);
        userEntity = userRepository.save(userEntity);

        return UserDto.builder().role(userEntity.getRole().toString()).build();
    }
}
