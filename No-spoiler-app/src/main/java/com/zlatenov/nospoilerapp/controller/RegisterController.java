package com.zlatenov.nospoilerapp.controller;

import com.zlatenov.nospoilersportsapi.model.exception.AuthorisationException;
import com.zlatenov.nospoilerapp.model.form.RegisterForm;
import com.zlatenov.nospoilerapp.service.UserServiceImpl;
import lombok.AllArgsConstructor;
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

    @GetMapping(value = {"/register", "/register.html"})
//    @PreAuthorize("isAnonymous()")
    public ModelAndView register(ModelAndView modelAndView, @ModelAttribute(name = "registerForm")
            RegisterForm registerForm) {
        modelAndView.addObject("registerForm", registerForm);

        return super.view("register", modelAndView);
    }

    @PostMapping(value = {"/register", "/register.html"})
//    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(ModelAndView modelAndView, @ModelAttribute(name = "registerForm")
            RegisterForm registerForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            registerForm.setPassword(null);
            registerForm.setConfirmPassword(null);
            modelAndView.addObject("registerForm", registerForm);

            return super.view("register", modelAndView);
        }

        try {
            this.userService.registerUser(registerForm);
        }
        catch (AuthorisationException e) {
            return super.view("register", modelAndView);
        }

        return super.redirect("/login");
    }

//    @GetMapping("/profile")
//    @PreAuthorize("isAuthenticated()")
//    @PageTitle("Profile")
//    public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
//        UserServiceModel userServiceModel = this.userService.findUserByUserName(principal.getName());
//        UserProfileViewModel model = this.modelMapper.map(userServiceModel, UserProfileViewModel.class);
//        modelAndView.addObject("model", model);
//
//        return super.view("user/profile", modelAndView);
//    }
//
//    @GetMapping("/edit")
//    @PreAuthorize("isAuthenticated()")
//    @PageTitle("Edit Profile")
//    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView, @ModelAttribute(name = "model") UserEditBindingModel model) {
//        UserServiceModel userServiceModel = this.userService.findUserByUserName(principal.getName());
//        model = this.modelMapper.map(userServiceModel, UserEditBindingModel.class);
//        model.setPassword(null);
//        modelAndView.addObject("model", model);
//
//        return super.view("user/edit-profile", modelAndView);
//    }
//
//    @PatchMapping("/edit")
//    @PreAuthorize("isAuthenticated()")
//    public ModelAndView editProfileConfirm(ModelAndView modelAndView, @ModelAttribute(name = "model") UserEditBindingModel model, BindingResult bindingResult) {
//        this.userEditValidator.validate(model, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            model.setOldPassword(null);
//            model.setPassword(null);
//            model.setConfirmPassword(null);
//            modelAndView.addObject("model", model);
//
//            return super.view("user/edit-profile", modelAndView);
//        }
//
//        UserServiceModel userServiceModel = this.modelMapper.map(model, UserServiceModel.class);
//        this.userService.editUserProfile(userServiceModel, model.getOldPassword());
//
//        return super.redirect("/users/profile");
//    }

}
