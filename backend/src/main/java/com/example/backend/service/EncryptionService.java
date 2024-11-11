package com.example.backend.service;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class EncryptionService {
    private final SecretKey secretKey;

    public EncryptionService() {
        // Pobranie klucza jako zmiennej środowiskowej
        String encodedKey = System.getenv("SECRET_KEY_BASE64");
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}
