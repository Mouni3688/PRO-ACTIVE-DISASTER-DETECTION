package com.proactive.disaster.services.impl;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proactive.disaster.services.AdminServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proactive.disaster.entities.Admin;
import com.proactive.disaster.helpers.AppConstants;
import com.proactive.disaster.helpers.ResourceNotFoundException;
import com.proactive.disaster.repsitories.AdminRepsitories;


@Service
public class AdminServicesImpl  implements AdminServices{


    @Autowired
    private AdminRepsitories adminRepsitories;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Admin saveAdmin(Admin admin) {
        // user id : have to generate
        String userId = UUID.randomUUID().toString();
        admin.setUserId(userId);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRoleList(List.of(AppConstants.ROLE_USER));
        logger.info(admin.getProvider().toString());
        String emailToken = UUID.randomUUID().toString();
        admin.setEmailToken(emailToken);
        Admin savedUser = adminRepsitories.save(admin);
       // String emailLink = helper.getLinkForEmailVerificatiton(emailToken);
       // emailService.sendEmail(savedUser.getEmail(), "Verify Account : Smart  Contact Manager", emailLink);
        return savedUser;
    }

    @Override
    public Optional<Admin> getAdminById(String id) {
        return adminRepsitories.findById(id);
    }

    @Override
    public Optional<Admin> updateUser(Admin user) {

        Admin user2 = adminRepsitories.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // update karenge user2 from user
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());
        // save the user in database
        Admin save = adminRepsitories.save(user2);
        return Optional.ofNullable(save);

    }

    @Override
    public void deleteAdmin(String id) {
        Admin user2 = adminRepsitories.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        adminRepsitories.delete(user2);
    }

    @Override
    public boolean isAdminExist(String userId) {
        Admin user2 = adminRepsitories.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isAdminExistByEmail(String email) {
        Admin user = adminRepsitories.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepsitories.findAll();
    }

    @Override
    public Admin getAdminByEmail(String email) {
        return adminRepsitories.findByEmail(email).orElse(null);
    }
    

}
