package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.exception.TeamDoesntExistException;
import com.zlatenov.spoilerfreeapp.exception.UserDoesntExistException;
import com.zlatenov.spoilerfreeapp.exception.VideoNotAvailableException;
import com.zlatenov.spoilerfreeapp.model.binding.LoginFormBindingModel;
import com.zlatenov.spoilerfreeapp.model.binding.RegisterFormBindingModel;
import com.zlatenov.spoilerfreeapp.model.binding.UserEditBindingModel;
import com.zlatenov.spoilerfreeapp.model.binding.UserRoleBindingModel;
import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.entity.User;
import com.zlatenov.spoilerfreeapp.model.entity.Video;
import com.zlatenov.spoilerfreeapp.model.enums.Role;
import com.zlatenov.spoilerfreeapp.model.service.UserServiceModel;
import com.zlatenov.spoilerfreeapp.model.service.VideoServiceModel;
import com.zlatenov.spoilerfreeapp.model.transformer.UserModelTransformer;
import com.zlatenov.spoilerfreeapp.model.transformer.VideoModelTransformer;
import com.zlatenov.spoilerfreeapp.repository.TeamRepository;
import com.zlatenov.spoilerfreeapp.repository.UserRepository;
import com.zlatenov.spoilerfreeapp.repository.VideoRepository;
import com.zlatenov.spoilerfreesportsapi.model.dto.user.EditUserDto;
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
    private UserRepository userRepository;
    private VideoRepository videoRepository;
    private TeamRepository teamRepository;
    private UserModelTransformer userModelTransformer;
    private VideoModelTransformer videoModelTransformer;
    private HashingService hashingService;

    @Override
    public void logUser(LoginFormBindingModel loginForm) throws AuthorisationException {
        LogUserDto authenticateUserDto = LogUserDto.builder()
                .username(loginForm.getUsername())
                .password(hashingService.hash(loginForm.getPassword()))
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
                .password(hashingService.hash(registerForm.getPassword()))
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
    public UserServiceModel getUserByUserName(String name) throws AuthorisationException {
        return userModelTransformer.transformToServiceModel(findUserByUserName(name));
    }

    public User findUserByUserName(String name) throws AuthorisationException {
        return Optional.of(userRepository.findByUsername(name)).orElseThrow(AuthorisationException::new);
    }

    @Override
    public void editUserProfile(UserEditBindingModel userEditBindingModel, String username) throws UserDoesntExistException {
        User user = getUser(username);

        String email = userEditBindingModel.getEmail() != null ? userEditBindingModel.getEmail() : user.getEmail();
        Integer age = userEditBindingModel.getAge() != null ? userEditBindingModel.getAge() : user.getAge();
        String country = userEditBindingModel.getCountry() != null ? userEditBindingModel.getCountry() : user.getCountry();

        EditUserDto editUserDto = EditUserDto.builder()
                .age(age)
                .country(userEditBindingModel.getCountry())
                .email(userEditBindingModel.getEmail())
                .username(username)
                .password(hashingService.hash(userEditBindingModel.getPassword()))
                .build();

        webClientBuilder.build()
                .post()
                .uri("localhost:8081/editProfile")
                .body(Mono.just(editUserDto), EditUserDto.class);

        user.setEmail(email);
        user.setAge(age);
        user.setCountry(country);

        userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserRoleBindingModel> getUserRoleViewModels() {
        return userModelTransformer.transformUserRoleViewModels(userRepository.findAll());
    }

    @Override
    public void changeRole(UserServiceModel userServiceModel) throws AuthorisationException {
        User user = findUserByUserName(userServiceModel.getName());
        user.setRole(userServiceModel.getRole());
        userRepository.saveAndFlush(user);
    }

    private void addUserToSession(UserDto loggedUserDto) throws AuthorisationException {
        Role role = Optional.ofNullable(loggedUserDto)
                .map(UserDto::getRole)
                .map(Role::valueOf)
                .orElseThrow(AuthorisationException::new);

        httpSession.setAttribute("role", role.toString());
    }

    @Override
    public void addRemoveFromFavorites(VideoServiceModel videoServiceModel, String name) throws AuthorisationException, VideoNotAvailableException {
        User user = Optional.of(userRepository.findByUsername(name))
                .orElseThrow(AuthorisationException::new);
        Video video = Optional.of(videoRepository.findByVideoId(videoServiceModel.getVideoId()))
                .orElseThrow(VideoNotAvailableException::new);

        List<Video> favoriteVideos = user.getFavoriteVideos();
        if (favoriteVideos.contains(video)) {
            favoriteVideos.remove(video);
        } else {
            favoriteVideos.add(video);
        }
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<VideoServiceModel> getFavourites(String username) throws AuthorisationException {
        return videoModelTransformer.transformToServiceModels(findUserByUserName(username).getFavoriteVideos());
    }

    @Override
    public void addRemoveFromWatchedTeams(String teamName, String username) throws TeamDoesntExistException, UserDoesntExistException {
        Team team = getTeam(teamName);
        User user = getUser(username);
        List<Team> watchedTeams = user.getWatchedTeams();
        if (watchedTeams.contains(team)) {
            watchedTeams.remove(team);
        } else {
            watchedTeams.add(team);
        }

        userRepository.saveAndFlush(user);

    }

    private User getUser(String username) throws UserDoesntExistException {
        return Optional.of(userRepository.findByUsername(username)).orElseThrow(UserDoesntExistException::new);
    }

    private Team getTeam(String teamName) throws TeamDoesntExistException {
        return Optional.of(teamRepository.findByFullName(teamName)).orElseThrow(TeamDoesntExistException::new);
    }

    @Override
    public void addRemoveFromFavoriteTeams(String teamName, String username) throws TeamDoesntExistException, UserDoesntExistException {
        Team team = getTeam(teamName);
        User user = getUser(username);
        List<Team> favoriteTeams = user.getFavoriteTeams();
        if (favoriteTeams.contains(team)) {
            favoriteTeams.remove(team);
        } else {
            favoriteTeams.add(team);
        }

        userRepository.saveAndFlush(user);
    }
}
