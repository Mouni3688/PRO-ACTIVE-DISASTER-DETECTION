package com.proactive.disaster.controller;


import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.proactive.disaster.entities.Admin;
import com.proactive.disaster.entities.SocialMedia;
import com.proactive.disaster.forms.ContactForm;
import com.proactive.disaster.forms.SocialMediaForm;
import com.proactive.disaster.helpers.Helper;
import com.proactive.disaster.helpers.Message;
import com.proactive.disaster.helpers.MessageType;
import com.proactive.disaster.services.AdminServices;
import com.proactive.disaster.services.ImageService;
import com.proactive.disaster.services.SocialmMediaServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/socialmedia")
public class SocialMediaController{
    @Autowired
    private SocialmMediaServices contactService;

    @Autowired
    private AdminServices userService;

    @Autowired
       private ImageService imageService;

    private Logger logger = org.slf4j.LoggerFactory.getLogger(SocialMediaController.class);

    @RequestMapping("/add")
    // add contact page: handler
    public String addContactView(Model model) {
        SocialMediaForm contactForm=new SocialMediaForm();
            contactForm.setFavorite(true);
            model.addAttribute("contactForm", contactForm);  
        return "admin/addToSocialmedia";
    }

    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute("contactForm") SocialMediaForm contactForm,BindingResult result,  
    Authentication authentication, HttpSession session) {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> logger.info(error.toString()));
            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "admin/addToSocialmedia"; // Return the form view again with validation errors
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        // form ---> contact
        Admin user = userService.getAdminByEmail(username);

        String filename=UUID.randomUUID().toString();

        String fileURL=imageService.uploadImage(contactForm.getContactImage(), filename);
    
        SocialMedia contact=new SocialMedia();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setAdmin(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(filename);
        contactService.save(contact);

        session.setAttribute("message",
        Message.builder()
                .content("You have successfully added a new contact")
                .type(MessageType.green)
                .build());
        return "redirect:/admin/socialmedia/add";

    }

    @RequestMapping
    public String viewContacts(Model model, Authentication authentication) {

        // load all the user contacts
        String username = Helper.getEmailOfLoggedInUser(authentication);

        Admin user = userService.getAdminByEmail(username);


       List<SocialMedia> contacts = contactService.getAllSocialMedia();

       model.addAttribute("contacts", contacts);
       
        return "admin/socialmedia";
    }
 @RequestMapping("/delete/{contactId}")
    public String deleteContact(
            @PathVariable("contactId") String contactId,
            HttpSession session) {
        contactService.delete(contactId);
        logger.info("contactId {} deleted", contactId);

        session.setAttribute("message",
                Message.builder()
                        .content("Contact is Deleted successfully !! ")
                        .type(MessageType.green)
                        .build()
        );

        return "redirect:/admin/socialmedia";
    }
    //for update
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(
            @PathVariable("contactId") String contactId,
            Model model) {

        var contact = contactService.getById(contactId);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setLinkedInLink(contact.getVolenter());
        contactForm.setPicture(contact.getPicture());
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);

        return "admin/update_contact_view";
    }

    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
            @Valid @ModelAttribute ContactForm contactForm,
            BindingResult bindingResult,
            Model model) {

        // update the contact
        if (bindingResult.hasErrors()) {
            return "admin/update_contact_view";
        }

        var con = contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setVolenter(contactForm.getVolenter());
        con.setLinkedInLink(contactForm.getLinkedInLink());

        // process image:

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);

        } else {
            logger.info("file is empty");
        }

        var updateCon = contactService.update(con);
        logger.info("updated contact {}", updateCon);

        model.addAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.green).build());

        return "redirect:/admin/socialmedia/view/" + contactId;
    }
    

   
}