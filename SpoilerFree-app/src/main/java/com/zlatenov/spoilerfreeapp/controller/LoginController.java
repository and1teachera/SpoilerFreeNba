package com.zlatenov.spoilerfreeapp.controller;

import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import com.zlatenov.spoilerfreeapp.model.form.LoginForm;
import com.zlatenov.spoilerfreeapp.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class LoginController extends BaseController {

    private final UserServiceImpl userService;

    @GetMapping(value = { "/login", "/login.html" })
    public ModelAndView login(ModelAndView modelAndView, HttpSession session,
            @ModelAttribute(name = "loginForm") LoginForm loginForm) {
        session.invalidate();
        modelAndView.addObject("loginForm", loginForm);
        return super.view("login");
    }

    @PostMapping(value = { "/login", "/login.html" })
    public ModelAndView login(ModelAndView modelAndView, @ModelAttribute(name = "loginForm") LoginForm loginForm,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            loginForm.setPassword(null);
            modelAndView.addObject("loginForm", loginForm);

            return super.view("login", modelAndView);
        }
        try {
            userService.logUser(loginForm);
        }
        catch (AuthorisationException e) {
            loginForm.setPassword(null);
            bindingResult.addError(new ObjectError("Login Error", e.getMessage()));
            modelAndView.addObject("loginForm", loginForm);

            return super.view("login", modelAndView);
        }

        return super.redirect("/index");
    }

}
