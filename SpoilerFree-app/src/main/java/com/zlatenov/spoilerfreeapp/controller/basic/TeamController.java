package com.zlatenov.spoilerfreeapp.controller.basic;

import com.zlatenov.spoilerfreeapp.exception.TeamDoesntExistException;
import com.zlatenov.spoilerfreeapp.exception.UserDoesntExistException;
import com.zlatenov.spoilerfreeapp.model.transformer.GamesModelTransformer;
import com.zlatenov.spoilerfreeapp.model.transformer.StandingsModelTransformer;
import com.zlatenov.spoilerfreeapp.model.view.StandingsViewModel;
import com.zlatenov.spoilerfreeapp.service.GameService;
import com.zlatenov.spoilerfreeapp.service.StandingsService;
import com.zlatenov.spoilerfreeapp.service.TeamService;
import com.zlatenov.spoilerfreeapp.service.UserService;
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
public class TeamController extends BaseController {

    private final GameService gamesService;
    private final GamesModelTransformer gamesModelTransformer;
    private final StandingsService standingsService;
    private final UserService userService;
    private final StandingsModelTransformer standingsModelTransformer;
    private final TeamService teamService;

    @GetMapping(value = "/{teamName}")
    public ModelAndView players(ModelAndView modelAndView, @PathVariable("teamName") String teamName) {
        modelAndView.addObject("games",
                gamesModelTransformer.transformToGameViewModels(gamesService.getGamesForTeam(teamName)));

        StandingsViewModel standingsViewModel = standingsModelTransformer.transformServiceModelToView(
                standingsService.getStandingsForTeam(teamName));

        modelAndView.addObject("standings", standingsViewModel);
        modelAndView.addObject("watched", standingsViewModel.getTeam().isWatched());
        modelAndView.addObject("favorite", standingsViewModel.getTeam().isFavorite());

        return view("team", modelAndView);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public void addRemoveFromSaved(@RequestParam("teamName") String teamName, Principal principal) {
        try {
            userService.addRemoveFromFavoriteTeams(teamName, principal.getName());
        } catch (TeamDoesntExistException e) {
            e.printStackTrace();
        } catch (UserDoesntExistException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/watchTeam")
    @PreAuthorize("isAuthenticated()")
    public void addRemoveFromWatched(@RequestParam("teamName") String teamName, Principal principal) {
        try {
            userService.addRemoveFromWatchedTeams(teamName, principal.getName());
        } catch (TeamDoesntExistException e) {
            e.printStackTrace();
        } catch (UserDoesntExistException e) {
            e.printStackTrace();
        }
    }
}
