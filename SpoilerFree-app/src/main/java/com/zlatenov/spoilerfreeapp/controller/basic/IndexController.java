package com.zlatenov.spoilerfreeapp.controller.basic;

import com.zlatenov.spoilerfreeapp.service.GameService;
import com.zlatenov.spoilerfreeapp.model.transformer.GamesModelTransformer;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

import static com.zlatenov.spoilerfreesportsapi.util.DateUtil.getCurrentDateWithoutTime;

/**
 * @author Angel Zlatenov
 */
@Controller
@AllArgsConstructor
public class IndexController extends BaseController {

    private final GameService gamesService;
    private final GamesModelTransformer gamesModelTransformer;

    @GetMapping({"/index", "/index.html", "/"})
    public ModelAndView index(ModelAndView modelAndView) {
        Date currentDate = getCurrentDateWithoutTime();
        modelAndView.addObject("games", gamesModelTransformer.transformToGameViewModels(gamesService.getGamesForDate(currentDate)));
        modelAndView.addObject("date", currentDate);
        modelAndView.addObject("days", gamesService.createDaysNavigationList(currentDate));
        return view("index", modelAndView);
    }

    @PostMapping("/selectDate")
    public void date(@RequestParam("date") String date, ModelAndView modelAndView) {
        Date selectedDate = DateUtil.parseDate(date);
        modelAndView.addObject("games", gamesModelTransformer.transformToGameViewModels(
                gamesService.getGamesForDate(selectedDate)));
        modelAndView.addObject("days", gamesService.createDaysNavigationList(selectedDate));
    }
}
