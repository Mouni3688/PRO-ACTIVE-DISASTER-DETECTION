package com.proactive.disaster.repsitories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proactive.disaster.entities.Admin;
import com.proactive.disaster.entities.SocialMedia;

public interface AdminRepsitories  extends JpaRepository<Admin, String> {

    Optional<Admin> findByEmail(String email);

    Optional<Admin> findByEmailAndPassword(String email, String password);

    Optional<Admin> findByEmailToken(String id);

    @Query("SELECT s FROM SocialMedia s WHERE s.admin = :admin")
    List<SocialMedia> findSocialMediasByAdmin(@Param("admin") Admin admin);
}
