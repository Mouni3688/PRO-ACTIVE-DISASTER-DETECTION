package com.proactive.disaster.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.proactive.disaster.entities.SocialMedia;
import com.proactive.disaster.helpers.SearchRequest;
import com.proactive.disaster.services.SocialmMediaServices;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    @Autowired
    private SocialmMediaServices contactService;

    @GetMapping("/socialmedia/{contactId}")
    public SocialMedia getContact(@PathVariable String contactId) {

        System.out.println("Results from repository query: " + contactId);
        return contactService.getById(contactId);
    }

    @PostMapping("/search")
    public ResponseEntity<List<SocialMedia>> search(@RequestBody SearchRequest request) {
        if (request.getField() == null || request.getValue() == null) {
            return ResponseEntity.badRequest().build();
        }
        List<SocialMedia> results = contactService.searchByField(request.getField(), request.getValue());
        return ResponseEntity.ok(results);
    }
}
