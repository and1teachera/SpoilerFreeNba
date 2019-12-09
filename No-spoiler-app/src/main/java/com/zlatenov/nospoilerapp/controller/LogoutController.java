package com.zlatenov.nospoilerapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

public class LogoutController extends BaseController {

    @GetMapping(value = { "/logout", "/logout.html" })
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return super.redirect("login");
    }
}
