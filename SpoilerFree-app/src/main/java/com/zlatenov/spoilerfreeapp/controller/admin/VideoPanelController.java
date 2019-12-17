package com.zlatenov.spoilerfreeapp.controller.admin;

import com.zlatenov.spoilerfreeapp.controller.basic.BaseController;
import com.zlatenov.spoilerfreeapp.model.binding.VideoBindingModel;
import com.zlatenov.spoilerfreeapp.model.view.VideoViewModel;
import com.zlatenov.spoilerfreeapp.service.VideoService;
import com.zlatenov.spoilerfreeapp.transformer.VideoModelTransformer;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Angel Zlatenov
 */

@Controller
@AllArgsConstructor
public class VideoPanelController extends BaseController {

    private VideoService videoService;
    private VideoModelTransformer videoModelTransformer;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping(value = "/admin/videoPanel/{date}")
    public ModelAndView videoPanel(@PathVariable("date") String date, ModelAndView modelAndView) {
        modelAndView.addObject("videos", videoModelTransformer.transformToViewModels(
                videoService.getVideosForDate(DateUtil.parseDate(date))));
        return view("videoPanel", modelAndView);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public void removeVideo(@RequestParam("video") VideoViewModel videoViewModel) {
        videoService.removeVideo(videoModelTransformer.transformToServiceModel(videoViewModel));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public void addVideo(@RequestParam("video") VideoBindingModel videoBindingModel) {
        videoService.addVideo(videoModelTransformer.transformToServiceModel(videoBindingModel));
    }

}
