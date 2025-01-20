package com.proactive.disaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.proactive.disaster.entities.Admin;
import com.proactive.disaster.forms.UserFrom;
import com.proactive.disaster.helpers.Message;
import com.proactive.disaster.helpers.MessageType;
import com.proactive.disaster.services.AdminServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private AdminServices adminService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        // sending data to view

        return "home";
    }

    // about route
    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin", true);
        System.out.println("About page loading");
        return "about";
    }

    // services
    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("services page loading");
        return "services";
    }
// voleneter

    @GetMapping("/trmsandcondition")
    public String trmsandcondition() {
        return new String("trmsandcondition");
    }

    @GetMapping("/volenter")
    public String volenter() {
        return new String("volenter");
    }
//people-user

    @GetMapping("/peopleuser")
    public String peopleuser() {
        return new String("peopleuser");
    }

    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

// registration page
    @GetMapping("/register")
    public String register(Model model) {
        UserFrom userForm = new UserFrom();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute("userForm") UserFrom userForm, BindingResult rBindingResult,
            HttpSession session) {
        System.out.println("Processing registration");
        // fetch form data
        // UserForm
        // validate form data
        if (rBindingResult.hasErrors()) {
            return "register";
        }
        Admin admin = new Admin();
        admin.setName(userForm.getName());
        admin.setEmail(userForm.getEmail());
        admin.setPassword(userForm.getPassword());
        admin.setAbout(userForm.getAbout());
        admin.setPhoneNumber(userForm.getPhoneNumber());
        admin.setEnabled(true);
        admin.setProfilePic(
                "");

        @SuppressWarnings("unused")
        Admin savedUser = adminService.saveAdmin(admin);

        System.out.println("user saved :");

        // message = "Registration Successful"
        // add the message:
        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();

        session.setAttribute("message", message);

        // redirectto login page
        return "redirect:/register";
    }

}
