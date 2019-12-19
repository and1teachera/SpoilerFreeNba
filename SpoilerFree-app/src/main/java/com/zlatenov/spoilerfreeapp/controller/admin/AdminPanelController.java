package com.zlatenov.spoilerfreeapp.controller.admin;

import com.zlatenov.spoilerfreeapp.controller.basic.BaseController;
import com.zlatenov.spoilerfreeapp.model.binding.UserRoleBindingModel;
import com.zlatenov.spoilerfreeapp.service.UserService;
import com.zlatenov.spoilerfreeapp.model.transformer.UserModelTransformer;
import com.zlatenov.spoilerfreesportsapi.model.exception.AuthorisationException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Angel Zlatenov
 */

@Controller
@AllArgsConstructor
public class AdminPanelController extends BaseController {

    private UserService userService;
    private UserModelTransformer userModelTransformer;

    @GetMapping(value = "/admin/panel")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ModelAndView panel(ModelAndView modelAndView) {
        modelAndView.addObject("users", userService.getUserRoleViewModels());
        return view("admin/adminPanel", modelAndView);
    }

    @PostMapping(value = "/admin/panel")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public void changeRole(@RequestParam("userRoleBindingModel") UserRoleBindingModel userRoleBindingModel) throws AuthorisationException {
        userService.changeRole(userModelTransformer.transformToServiceModel(userRoleBindingModel));
    }

    @ExceptionHandler(AuthorisationException.class)
    public ModelAndView authorisation(ModelAndView modelAndView){
        modelAndView.addObject("message", "You are not allowed to see this page!");

        return view("basic/error", modelAndView);
    }

}
