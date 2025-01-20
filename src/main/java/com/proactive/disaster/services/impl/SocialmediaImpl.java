package com.proactive.disaster.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proactive.disaster.entities.Admin;
import com.proactive.disaster.entities.SocialMedia;
import com.proactive.disaster.helpers.ResourceNotFoundException;
import com.proactive.disaster.repsitories.SocialMEdiaRepository;
import com.proactive.disaster.services.SocialmMediaServices;



@Service
public class SocialmediaImpl implements SocialmMediaServices{
    @Autowired
    private SocialMEdiaRepository contactRepo;

    @Override
    public SocialMedia save(SocialMedia contact) {

        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);

    }

    @Override
    public SocialMedia update(SocialMedia contact) {

        var contactOld = contactRepo.findById(contact.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());
        contactOld.setPicture(contact.getPicture());
        contactOld.setFavorite(contact.isFavorite());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());

        return contactRepo.save(contactOld);
    }

    @Override
    public List<SocialMedia> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public SocialMedia getById(String id) {
        return contactRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Contact not found with ID: " + id));
    }
    
    @Override
    public void delete(String id) {
        var contact = contactRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id " + id));
contactRepo.delete(contact);
    }

    @Override
    public List<SocialMedia> getBySocialMediaId(String userId) {
        return contactRepo.findByAdminId(userId);
    }

    @Override
    public List<SocialMedia> getByAdmin(Admin user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByAdmin'");
    }

    @Override
    public List<SocialMedia> getAllSocialMedia() {
        return contactRepo.findAll();
    }

    @Override
    public List<SocialMedia> getByEmail(Admin user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByEmail'");
    }


    @Override
    public List<SocialMedia> findByAdminId(String adminId) {
        return contactRepo.findByAdminId(adminId);
    }
    

    public List<SocialMedia> searchByField(String field, String value) {
        if ("name".equalsIgnoreCase(field)) {
            return contactRepo.findByNameContainingIgnoreCase(value);
        } else if ("email".equalsIgnoreCase(field)) {
            return contactRepo.findByEmailContainingIgnoreCase(value);
        }
        return List.of(); // Return empty list for unsupported fields
    }
    



}
