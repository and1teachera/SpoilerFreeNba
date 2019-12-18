package com.zlatenov.spoilerfreeapp.controller.basic;

import com.zlatenov.spoilerfreeapp.exception.VideoNotAvailableException;
import com.zlatenov.spoilerfreeapp.model.view.VideoViewModel;
import com.zlatenov.spoilerfreeapp.service.GameService;
import com.zlatenov.spoilerfreeapp.service.StandingsService;
import com.zlatenov.spoilerfreeapp.service.UserService;
import com.zlatenov.spoilerfreeapp.service.VideoService;
import com.zlatenov.spoilerfreeapp.model.transformer.GamesModelTransformer;
import com.zlatenov.spoilerfreeapp.model.transformer.StandingsModelTransformer;
import com.zlatenov.spoilerfreeapp.model.transformer.VideoModelTransformer;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * @author Angel Zlatenov
 */

@Controller
@AllArgsConstructor
public class GameVideosController extends BaseController {

    private final GameService gamesService;
    private final GamesModelTransformer gamesModelTransformer;
    private final StandingsService standingsService;
    private final StandingsModelTransformer standingsModelTransformer;
    private final VideoService videoService;
    private final VideoModelTransformer videoModelTransformer;
    private final UserService userService;

    @GetMapping("video/{date}/{gameName}")
    public ModelAndView videos(@PathVariable("gameName") String gameName, @PathVariable("date") String date ,ModelAndView modelAndView) {
        modelAndView.addObject("gameInformation", gamesModelTransformer.transformToGameViewModel(
                gamesService.getGameInformation(gameName, date)));
        modelAndView.addObject("standingsInformation", standingsModelTransformer.transformServiceModelsToDayViews(
                standingsService.getStandingsInformation(gameName, date)));
        modelAndView.addObject("videoList", videoModelTransformer.transformToViewModels(videoService.getVideosForDate(
                DateUtil.parseDate(date))));
        return view("gameVideos", modelAndView);
    }

    @PostMapping("/saveVideo")
    @PreAuthorize("isAuthenticated()")
    public void addRemoveFromSaved(@RequestParam("video") VideoViewModel video, Principal principal) {
        try {
            userService.addRemoveFromFavorites(videoModelTransformer.transformToServiceModel(video), principal.getName());
        } catch (AuthorisationException e) {
            e.printStackTrace();
        } catch (VideoNotAvailableException e) {
            e.printStackTrace();
        }
    }

}
