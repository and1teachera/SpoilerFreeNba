package com.zlatenov.nospoilerapp.controller;

import com.zlatenov.nospoilerapp.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
        modelAndView.addObject(gamesService.getAllGames());
        return modelAndView;
    }
}
