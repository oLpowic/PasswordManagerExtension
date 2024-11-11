package com.example.backend.controller;

import com.example.backend.dto.CredentialRequest;
import com.example.backend.model.User;
import com.example.backend.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@RestController
@RequestMapping("/api/credentials")
public class CredentialController {
    @Autowired
    private CredentialService credentialService;

    @PostMapping("/add")
    public ResponseEntity<?> addCredential(@RequestBody CredentialRequest request, @AuthenticationPrincipal User user) {
        try {
            String result = credentialService.saveCredential(user, request.getLogin(), request.getPassword(), request.getUrl());
            if ("URL already exists for this user.".equals(result)) {
                return ResponseEntity.status(409).body(result); // Konflikt (409) - URL już istnieje
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving credential");
        }
    }
}

