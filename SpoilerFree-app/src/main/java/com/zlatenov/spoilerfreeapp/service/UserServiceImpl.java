package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.form.LoginForm;
import com.zlatenov.spoilerfreeapp.model.form.RegisterForm;
import com.zlatenov.spoilerfreesportsapi.enums.Role;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.LogUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.RegisterUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.UserDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import lombok.AllArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author Angel Zlatenov
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final WebClient.Builder webClientBuilder;
    private final HttpSession httpSession;

    @Override
    public void logUser(LoginForm loginForm) throws AuthorisationException {
        LogUserDto authenticateUserDto = LogUserDto.builder()
                .username(loginForm.getUsername())
                .password(loginForm.getPassword())
                .build();

        UserDto loggedUserDto = webClientBuilder.build()
                .post()
                .uri("localhost:8081/login")
                .body(Mono.just(authenticateUserDto), LogUserDto.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                          clientResponse -> clientResponse
                                  .bodyToMono(String.class)
                                  .map(AuthenticationException::new))
                .bodyToMono(UserDto.class)
                .block();

        addUserToSession(loggedUserDto);
    }

    @Override
    public void registerUser(RegisterForm registerForm) throws AuthorisationException {
        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .username(registerForm.getUsername())
                .password(registerForm.getPassword())
                .email(registerForm.getEmail())
                .build();

        UserDto loggedUserDto = webClientBuilder.build()
                .post()
                .uri("localhost:8081/register")
                .body(Mono.just(registerUserDto), RegisterUserDto.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                          clientResponse -> clientResponse
                                  .bodyToMono(String.class)
                                  .map(AuthenticationException::new))
                .bodyToMono(UserDto.class)
                .block();

        addUserToSession(loggedUserDto);
    }

    private void addUserToSession(UserDto loggedUserDto) throws AuthorisationException {
        Role role = Optional.ofNullable(loggedUserDto)
                .map(UserDto::getRole)
                .orElseThrow(AuthorisationException::new);

        httpSession.setAttribute("role", role.toString());
    }
}
