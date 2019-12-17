package com.zlatenov.spoilerfreeapp.controller.admin;

import com.zlatenov.spoilerfreeapp.controller.basic.BaseController;
import com.zlatenov.spoilerfreeapp.model.binding.UserRoleBindingModel;
import com.zlatenov.spoilerfreeapp.service.UserService;
import com.zlatenov.spoilerfreeapp.transformer.UserModelTransformer;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
    public ModelAndView adminPanel(ModelAndView modelAndView) {
        modelAndView.addObject("users", userService.getUserRoleViewModels());
        return view("adminPanel", modelAndView);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public void changeRole(@RequestParam("userRoleBindingModel") UserRoleBindingModel userRoleBindingModel) {
        userService.changeRole(userModelTransformer.transformToServiceModel(userRoleBindingModel));
    }

}
