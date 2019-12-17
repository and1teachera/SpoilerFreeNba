package com.zlatenov.spoilerfreeapp.controller.basic;

import com.zlatenov.spoilerfreeapp.service.TeamService;
import com.zlatenov.spoilerfreeapp.transformer.TeamModelTransformer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Angel Zlatenov
 */

@Controller
@AllArgsConstructor
public class PlayersController extends BaseController {

    private final TeamService teamService;
    private final TeamModelTransformer teamModelTransformer;

    @GetMapping(value = "/{teamName}/players")
    public ModelAndView players(ModelAndView modelAndView, @PathVariable("teamName") String teamName) {
        modelAndView.addObject("players",
                teamModelTransformer.transformToPlayersViewModel(teamService.getPlayersByTeamName(teamName)));
        return view("players", modelAndView);
    }
}
