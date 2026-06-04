package com.bottelx.security;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenGenerator {

    public String generateRawToken(UUID id) {
        return id + "." + UUID.randomUUID();
    }

    public String hash(String secret) {
        return BCrypt.hashpw(secret, BCrypt.gensalt(12));
    }
}
