package com.tmus.prel.example.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {
    private String idToken;
    private String accessToken;
    private Integer expiresIn;
    private String tokenType;
    private String resource;
    private String refreshToken;
    private String scope;
}
