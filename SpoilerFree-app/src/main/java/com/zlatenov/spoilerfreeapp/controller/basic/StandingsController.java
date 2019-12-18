package com.zlatenov.spoilerfreeapp.controller.basic;

import com.zlatenov.spoilerfreeapp.service.StandingsService;
import com.zlatenov.spoilerfreeapp.model.transformer.StandingsModelTransformer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Angel Zlatenov
 */

@Controller
@AllArgsConstructor
public class StandingsController extends BaseController {

    private final StandingsService standingsService;
    private final StandingsModelTransformer standingsModelTransformer;


    @GetMapping(value = "/standings")
    public ModelAndView standings(ModelAndView modelAndView) {
        modelAndView.addObject("standings", standingsModelTransformer.transformServiceModelsToViews(
                standingsService.getCurrentStandings()));
        return view("standings", modelAndView);
    }
}
