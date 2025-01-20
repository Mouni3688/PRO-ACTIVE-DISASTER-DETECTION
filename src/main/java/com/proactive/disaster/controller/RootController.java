package com.proactive.disaster.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.proactive.disaster.entities.Admin;
import com.proactive.disaster.helpers.Helper;
import com.proactive.disaster.services.AdminServices;
@ControllerAdvice
public class RootController {
private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminServices userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null) {
            return;
        }
        System.out.println("Adding logged in user information to the model");
        ;
        String loggedInUser=Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User loged in :{}",loggedInUser);
        Admin admin=userService.getAdminByEmail(loggedInUser);
        model.addAttribute("loggedInUser", admin);
        System.out.println("profile page");

    }
}
