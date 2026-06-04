package com.bottelx.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class TokenBlacklist {

    private final Map<String, Long> blacklistedTokens = new ConcurrentHashMap<>();

    public void blacklistToken(String token, long expiryTime) {
        blacklistedTokens.put(token, expiryTime);
    }

    public boolean isBlacklisted(String token) {
        Long expiry = blacklistedTokens.get(token);

        if (expiry == null) {
            return false;
        }

        // Remove expired blacklist entries
        if (expiry < System.currentTimeMillis()) {
            blacklistedTokens.remove(token);
            return false;
        }
        return true;
    }
}

