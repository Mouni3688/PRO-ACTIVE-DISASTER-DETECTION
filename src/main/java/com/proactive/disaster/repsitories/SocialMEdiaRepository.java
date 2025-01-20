package com.proactive.disaster.repsitories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proactive.disaster.entities.Admin;
import com.proactive.disaster.entities.SocialMedia;
@Repository
public interface SocialMEdiaRepository  extends JpaRepository<SocialMedia, String> {

   // Page<SocialMedia> findByUser(SocialMedia user, Pageable pageable);

    // custom query method
    List<SocialMedia> findByAdmin(Admin admin);

    @Query("SELECT c FROM SocialMedia c WHERE c.admin.id = :userId")
    List<SocialMedia> findByAdminId(@Param("userId") String userId);

    List<SocialMedia> findByNameContainingIgnoreCase(String name);

    List<SocialMedia> findByEmailContainingIgnoreCase(String email);

    List<SocialMedia> findByPhoneNumberContainingIgnoreCase(String phone);

    
    

}