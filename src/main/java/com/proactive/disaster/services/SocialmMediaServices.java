package com.proactive.disaster.services;

import java.util.List;

import com.proactive.disaster.entities.Admin;
import com.proactive.disaster.entities.SocialMedia;

public interface SocialmMediaServices {
    SocialMedia save(SocialMedia contact);

    // update contact
    SocialMedia update(SocialMedia contact);

    // get contacts
    List<SocialMedia> getAll();

    // get contact by id

    SocialMedia getById(String id);

    // delete contact

    void delete(String id);

   // search contact
   
 
    // get contacts by userId
    List<SocialMedia> getBySocialMediaId(String userId);

    public List<SocialMedia> getByAdmin(Admin user);

    public List<SocialMedia> getAllSocialMedia();
    
    public List<SocialMedia> getByEmail(Admin user);

    List<SocialMedia> findByAdminId(String adminId); 
    public List<SocialMedia> searchByField(String field, String value);

 
}
