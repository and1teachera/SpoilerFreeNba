package com.zlatenov.spoilerfreeapp.controller.user;

import com.zlatenov.spoilerfreeapp.controller.basic.BaseController;
import com.zlatenov.spoilerfreeapp.exception.UserDoesntExistException;
import com.zlatenov.spoilerfreeapp.model.binding.UserEditBindingModel;
import com.zlatenov.spoilerfreeapp.model.service.UserServiceModel;
import com.zlatenov.spoilerfreeapp.model.validator.UserEditValidator;
import com.zlatenov.spoilerfreeapp.model.view.UserProfileViewModel;
import com.zlatenov.spoilerfreeapp.service.UserService;
import com.zlatenov.spoilerfreeapp.model.transformer.UserModelTransformer;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * @author Angel Zlatenov
 */

@Controller
@AllArgsConstructor
public class ProfileController extends BaseController {

    private final UserService userService;
    private final UserModelTransformer userModelTransformer;
    private final UserEditValidator userEditValidator;

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView,
                                @ModelAttribute(name = "userBindingModel") UserEditBindingModel userBindingModel) {
        UserServiceModel userServiceModel = null;
        try {
            userServiceModel = this.userService.getUserByUserName(principal.getName());
        } catch (AuthorisationException e) {
            e.printStackTrace();
        }
        UserProfileViewModel userViewModel = userModelTransformer.transformUserToViewModel(userServiceModel);
        userBindingModel.setPassword(null);
        modelAndView.addObject("user", userBindingModel);

        return view("profile", modelAndView);
    }

    @PatchMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(ModelAndView modelAndView,
                                    @ModelAttribute(name = "userBindingModel") UserEditBindingModel userBindingModel,
                                           BindingResult bindingResult, Principal principal) {
        this.userEditValidator.validate(userBindingModel, bindingResult);

        if (bindingResult.hasErrors()) {
            userBindingModel.setPassword(null);
            userBindingModel.setNewPassword(null);
            userBindingModel.setConfirmPassword(null);
            modelAndView.addObject("model", userBindingModel);

            return view("profile", modelAndView);
        }

        try {
            this.userService.editUserProfile(userBindingModel, principal.getName());
        } catch (UserDoesntExistException e) {
            e.printStackTrace();
        }

        return view("profile", modelAndView);
    }
}
