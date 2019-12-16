package com.zlatenov.spoilerfreeapp.controller;

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


    @GetMapping(value = { "/standings", "/standings.html" })
    public ModelAndView standings() {
        return super.view("standings");
    }
}
