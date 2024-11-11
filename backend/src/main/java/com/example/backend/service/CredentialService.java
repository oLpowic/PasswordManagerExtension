package com.example.backend.service;

import com.example.backend.model.Credential;
import com.example.backend.model.User;
import com.example.backend.repository.CredentialRepository;
import com.example.backend.utils.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Optional;

@Service
public class CredentialService {
    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private EncryptionService encryptionService;

    public String saveCredential(User user, String login, String password, String url) throws Exception {
        SecretKey key = encryptionService.getSecretKey();
        byte[] encryptedUrl = EncryptionUtils.encrypt(url, key);

        // Sprawdzenie, czy dany URL już istnieje dla użytkownika
        if (credentialRepository.existsByUserAndUrl(user, encryptedUrl)) {
            return "URL already exists for this user.";
        }

        // Szyfrowanie loginu i hasła
        byte[] encryptedLogin = EncryptionUtils.encrypt(login, key);
        byte[] encryptedPassword = EncryptionUtils.encrypt(password, key);

        Credential credential = new Credential();
        credential.setUser(user);
        credential.setLogin(encryptedLogin);
        credential.setPassword(encryptedPassword);
        credential.setUrl(encryptedUrl);

        credentialRepository.save(credential);
        return "Credential saved successfully.";
    }
    public Optional<Credential> findCredentialByUrl(User user, String url) throws Exception {
        SecretKey key = encryptionService.getSecretKey();
        byte[] encryptedUrl = EncryptionUtils.encrypt(url, key);
        return credentialRepository.findByUserAndUrl(user, encryptedUrl);
    }

    public Optional<String> getDecryptedCredential(User user, String url) throws Exception {
        SecretKey key = encryptionService.getSecretKey();
        byte[] encryptedUrl = EncryptionUtils.encrypt(url, key);

        Optional<Credential> credentialOpt = credentialRepository.findByUserAndUrl(user, encryptedUrl);

        if (credentialOpt.isPresent()) {
            Credential credential = credentialOpt.get();
            String decryptedLogin = EncryptionUtils.decrypt(credential.getLogin(), key);
            String decryptedPassword = EncryptionUtils.decrypt(credential.getPassword(), key);
            String decryptedUrl = EncryptionUtils.decrypt(credential.getUrl(), key);

            return Optional.of("Login: " + decryptedLogin + ", Password: " + decryptedPassword + ", URL: " + decryptedUrl);
        }
        return Optional.empty();
    }
}
