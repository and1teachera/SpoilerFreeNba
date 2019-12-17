package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.binding.LoginFormBindingModel;
import com.zlatenov.spoilerfreeapp.model.binding.RegisterFormBindingModel;
import com.zlatenov.spoilerfreeapp.model.binding.UserRoleBindingModel;
import com.zlatenov.spoilerfreeapp.model.enums.Role;
import com.zlatenov.spoilerfreeapp.model.service.UserServiceModel;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.LogUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.RegisterUserDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.UserDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import lombok.AllArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * @author Angel Zlatenov
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final WebClient.Builder webClientBuilder;
    private final HttpSession httpSession;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void logUser(LoginFormBindingModel loginForm) throws AuthorisationException {
        LogUserDto authenticateUserDto = LogUserDto.builder()
                .username(loginForm.getUsername())
                .password(bCryptPasswordEncoder.encode(loginForm.getPassword()))
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
    public void registerUser(RegisterFormBindingModel registerForm) throws AuthorisationException {
        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .username(registerForm.getUsername())
                .password(bCryptPasswordEncoder.encode(registerForm.getPassword()))
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

    @Override
    public UserServiceModel findUserByUserName(String name) {
        return null;
    }

    @Override
    public void editUserProfile(UserServiceModel user) {

    }

    @Override
    public List<UserRoleBindingModel> getUserRoleViewModels() {
        return null;
    }

    @Override
    public void changeRole(UserServiceModel transformToServiceModel) {

    }

    private void addUserToSession(UserDto loggedUserDto) throws AuthorisationException {
        Role role = Optional.ofNullable(loggedUserDto)
                .map(UserDto::getRole)
                .map(Role::valueOf)
                .orElseThrow(AuthorisationException::new);

        httpSession.setAttribute("role", role.toString());
    }
}
