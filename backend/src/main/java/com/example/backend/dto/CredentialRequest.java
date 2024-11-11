package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialRequest {
    private String login;
    private String password;
    private String url;
}