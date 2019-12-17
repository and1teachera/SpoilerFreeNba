package com.zlatenov.spoilerfreeapp.controller.user;

import com.zlatenov.spoilerfreeapp.controller.basic.BaseController;
import com.zlatenov.spoilerfreeapp.model.view.VideoViewModel;
import com.zlatenov.spoilerfreeapp.service.VideoService;
import com.zlatenov.spoilerfreeapp.transformer.VideoModelTransformer;
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

    private final VideoService videoService;
    private final VideoModelTransformer videoModelTransformer;

    @GetMapping(value = "/favorites")
    public ModelAndView favorites(Principal principal, ModelAndView modelAndView) {
        modelAndView.addObject("games",
                videoModelTransformer.transformToViewModels(videoService.getFavourites(principal.getName())));
        return view("favorites", modelAndView);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public void addRemoveFromFavorites(Principal principal, @RequestParam("video") VideoViewModel video) {
        videoService.addRemoveFromFavorites(videoModelTransformer.transformToServiceModel(video), principal.getName());
    }
}
