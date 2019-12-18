package com.zlatenov.spoilerfreeapp.controller.user;

import com.zlatenov.spoilerfreeapp.controller.basic.BaseController;
import com.zlatenov.spoilerfreeapp.exception.VideoNotAvailableException;
import com.zlatenov.spoilerfreeapp.model.transformer.VideoModelTransformer;
import com.zlatenov.spoilerfreeapp.model.view.VideoViewModel;
import com.zlatenov.spoilerfreeapp.service.UserService;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * @author Angel Zlatenov
 */

@Controller
@AllArgsConstructor
public class FavoritesController extends BaseController {

    private UserService userService;
    private final VideoModelTransformer videoModelTransformer;

    @GetMapping(value = "/favorites")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView favorites(Principal principal, ModelAndView modelAndView) {
        try {
            modelAndView.addObject("games",
                    videoModelTransformer.transformToViewModels(userService.getFavourites(principal.getName())));
        } catch (AuthorisationException e) {
            e.printStackTrace();
        }
        return view("favorites", modelAndView);
    }

    @PostMapping("/addRemoveFromFavorites")
    @PreAuthorize("isAuthenticated()")
    public void addRemoveFromFavorites(Principal principal, @RequestParam("video") VideoViewModel video) {
        try {
            userService.addRemoveFromFavorites(videoModelTransformer.transformToServiceModel(video), principal.getName());
        } catch (AuthorisationException e) {
            e.printStackTrace();
        } catch (VideoNotAvailableException e) {
            e.printStackTrace();
        }
    }
}
