package com.example.backend.repository;

import com.example.backend.model.Credential;
import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
    List<Credential> findByUser(User user);

    @Query("SELECT c FROM Credential c WHERE c.user = :user AND c.url = :url")
    Optional<Credential> findByUserAndUrl(@Param("user") User user, @Param("url") byte[] url);

    @Query("SELECT COUNT(c) > 0 FROM Credential c WHERE c.user = :user AND c.url = :url")
    boolean existsByUserAndUrl(@Param("user") User user, @Param("url") byte[] url);
}
