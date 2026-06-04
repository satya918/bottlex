package com.bottelx.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.bottelx.entity.RefreshToken;
import com.bottelx.entity.User;
import com.bottelx.repository.RefreshTokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RefreshTokenService {

	@Autowired
	private RefreshTokenRepository repo;

	@Autowired
	private RefreshTokenGenerator generator;

	@Autowired
	private JwtUtil jwtUtil;

	/* ================= CREATE (LOGIN) ================= */
	public String create(User user, HttpServletRequest req) {

		UUID id = UUID.randomUUID();
		String rawToken = generator.generateRawToken(id);

		String secret = rawToken.split("\\.")[1];

		RefreshToken token = new RefreshToken();
		token.setId(id);
		token.setTokenHash(generator.hash(secret));
		token.setUser(user);
		token.setExpiry(Instant.now().plus(30, ChronoUnit.DAYS));
		token.setUserAgent(req.getHeader("User-Agent"));
		token.setIpAddress(req.getRemoteAddr());
		token.setCreatedAt(Instant.now());
		token.setRevoked(false);

		repo.save(token);
		return rawToken;
	}

	/* ================= ROTATION (REFRESH) ================= */
	public RefreshTokenRotationResult rotate(String rawToken, HttpServletRequest request) {

		String[] parts = rawToken.split("\\.");
		UUID tokenId = UUID.fromString(parts[0]);
		String secret = parts[1];

		RefreshToken token = repo.findById(tokenId).orElseThrow(() -> new SecurityException("Invalid refresh token"));

		// 🔥 REUSE DETECTED
		if (token.isRevoked()) {
			repo.revokeAllByUser(token.getUser().getId());
			throw new SecurityException("Refresh token reuse detected. All sessions revoked.");
		}
		if (token.getUser() == null) {
			throw new SecurityException("Invalid token owner");
		}

		if (token.getExpiry().isBefore(Instant.now())) {
			throw new SecurityException("Refresh token expired");
		}

		if (!BCrypt.checkpw(secret, token.getTokenHash())) {
			repo.revokeAllByUser(token.getUser().getId());
			throw new SecurityException("Refresh token compromised");
		}

		// 🔁 Rotate
		token.setRevoked(true);
		token.setReused(true);
		repo.save(token);

		User user = token.getUser();

		long activeSessions = repo.countByUser_IdAndRevokedFalse(user.getId());

		int maxSessions =   user.hasRole("ADMIN") ? 1 : 2;

		// if (activeSessions >= maxSessions) {
		// repo.revokeOldestSessions(user.getId(), activeSessions - maxSessions + 1);
		// }

		// 🔐 Create new token
		UUID newId = UUID.randomUUID();
		String newRaw = generator.generateRawToken(newId);
		String newSecret = newRaw.split("\\.")[1];

		RefreshToken fresh = new RefreshToken();
		fresh.setId(newId);
		fresh.setUser(user);
		fresh.setTokenHash(generator.hash(newSecret));
		fresh.setExpiry(Instant.now().plus(30, ChronoUnit.DAYS));
		fresh.setIpAddress(request.getRemoteAddr());
		fresh.setUserAgent(request.getHeader("User-Agent"));
		fresh.setCreatedAt(Instant.now());
		fresh.setRevoked(false);
		fresh.setReused(false);

		repo.save(fresh);

		return new RefreshTokenRotationResult(jwtUtil.generateAccessToken(user), newRaw, user);
	}

	/* ================= LOGOUT ================= */
	public void revoke(String rawToken) {
		try {
			UUID id = UUID.fromString(rawToken.split("\\.")[0]);
			repo.findById(id).ifPresent(t -> t.setRevoked(true));
		} catch (Exception ignored) {
		}
	}

	public void revokeAll(UUID userId) {
		repo.revokeAllByUser(userId);
	}
}
