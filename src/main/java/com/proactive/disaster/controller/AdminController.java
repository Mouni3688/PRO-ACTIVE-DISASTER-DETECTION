package com.proactive.disaster.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.proactive.disaster.services.AdminServices;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @SuppressWarnings({"FieldMayBeFinal", "unused"})
    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    @SuppressWarnings("unused")
    private AdminServices adminService;

    @RequestMapping(value = "/dashboard")
    public String adminDashboard() {
        System.out.println("User dashboard");
        return "admin/dashboard";
    }

    // user profile page
    @RequestMapping(value = "/profile")
    public String adminprofile(Model model, Authentication authentication) {
        // System.out.println(authentication);
        System.out.println("profile page");
        return "admin/profile";
    }
    // user add contacts page
    // user view contacts
    // user edit contact
    // user delete contact
}
