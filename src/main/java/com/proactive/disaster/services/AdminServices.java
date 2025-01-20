package com.proactive.disaster.services;

import java.util.List;
import java.util.Optional;

import com.proactive.disaster.entities.Admin;




public interface AdminServices {
    Admin saveAdmin(Admin admin);

    Optional<Admin> getAdminById(String id);

    Optional<Admin> updateUser(Admin admin);

    void deleteAdmin(String id);

    boolean isAdminExist(String userId);

    boolean isAdminExistByEmail(String email);

    List<Admin> getAllAdmins();



    // add more methods here related user service[logic]
    public Admin getAdminByEmail(String email);
}
