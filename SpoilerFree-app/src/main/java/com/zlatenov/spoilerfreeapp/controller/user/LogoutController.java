package com.zlatenov.spoilerfreeapp.controller.user;

import com.zlatenov.spoilerfreeapp.controller.basic.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController extends BaseController {

    @GetMapping(value = "/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return super.redirect("login");
    }
}
