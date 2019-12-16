package com.zlatenov.spoilerfreeapp.controller;

import com.zlatenov.spoilerfreeapp.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping({"/index", "/index.html", "/"})
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index.html");
        Date currentDate = getCurrentDateWithoutTime();
        modelAndView.addObject("games", gamesService.getGameViewModelsForDate(currentDate));
        modelAndView.addObject("date", currentDate);
        modelAndView.addObject("days", gamesService.createDaysNavigationList(currentDate));
        return modelAndView;
    }
}
