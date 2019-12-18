package com.zlatenov.spoilerfreeapp.controller.user;

import com.zlatenov.spoilerfreeapp.controller.basic.BaseController;
import com.zlatenov.spoilerfreeapp.model.binding.RegisterFormBindingModel;
import com.zlatenov.spoilerfreeapp.service.UserServiceImpl;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class RegisterController extends BaseController {

    private final UserServiceImpl userService;

    @GetMapping(value = "/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(ModelAndView modelAndView, @ModelAttribute(name = "registerForm")
            RegisterFormBindingModel registerForm) {
        modelAndView.addObject("registerForm", registerForm);

        return view("register", modelAndView);
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(ModelAndView modelAndView, @ModelAttribute(name = "registerForm")
            RegisterFormBindingModel registerForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            registerForm.setPassword(null);
            registerForm.setConfirmPassword(null);
            modelAndView.addObject("registerForm", registerForm);

            return view("register", modelAndView);
        }

        try {
            this.userService.registerUser(registerForm);
        }
        catch (AuthorisationException e) {
            return view("register", modelAndView);
        }

        return redirect("/login");
    }


}
