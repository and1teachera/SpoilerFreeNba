package com.zlatenov.spoilerfreeapp.controller.user;

import com.zlatenov.spoilerfreeapp.controller.basic.BaseController;
import com.zlatenov.spoilerfreeapp.model.binding.LoginFormBindingModel;
import com.zlatenov.spoilerfreeapp.service.UserService;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
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

    private final UserService userService;

    @GetMapping(value = "/login")
    public ModelAndView login(ModelAndView modelAndView, HttpSession session,
                              @ModelAttribute(name = "loginForm") LoginFormBindingModel loginForm) {
        session.invalidate();
        modelAndView.addObject("loginForm", loginForm);
        return view("login", modelAndView);
    }

    @PostMapping
    public ModelAndView login(ModelAndView modelAndView, @ModelAttribute(name = "loginForm") LoginFormBindingModel loginForm,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            loginForm.setPassword(null);
            modelAndView.addObject("loginForm", loginForm);

            return view("login", modelAndView);
        }
        try {
            userService.logUser(loginForm);
        } catch (AuthorisationException e) {
            loginForm.setPassword(null);
            bindingResult.addError(new ObjectError("Login Error", e.getMessage()));
            modelAndView.addObject("loginForm", loginForm);

            return view("login", modelAndView);
        }

        return redirect("/index");
    }

}
