package com.bottelx.security;

import java.util.Date;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bottelx.entity.User;
 
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final byte[] SECRET = "BOTTLEX_SECURE_KEY_2026_hgfdfgu8765edtfhb_rtyu9875t6y@#@#2341".getBytes();

	/* ================= ACCESS TOKEN ================= */

	public String generateAccessToken(User user) {
		return Jwts.builder().setSubject(user.getUserName()).setIssuedAt(new Date()).claim("role", user.getRoles())
				.setId(UUID.randomUUID().toString())
				.setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))// 15 minutes access token

				.signWith(Keys.hmacShaKeyFor(SECRET), SignatureAlgorithm.HS256).compact();
	}

	public boolean validateAccessToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	/* ================= REFRESH TOKEN ================= */

	public String generateRefreshToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000))
				.signWith(Keys.hmacShaKeyFor(SECRET), SignatureAlgorithm.HS256).compact();
	}

	public boolean validateRefreshToken(String token) {
		try {
			extractUsername(token);
			return !isTokenExpired(token);
		} catch (Exception e) {
			return false;
		}
	}

	/* ================= COMMON ================= */

	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody().getSubject();
	}

	private boolean isTokenExpired(String token) {
		Date exp = Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody().getExpiration();
		return exp.before(new Date());
	}

	public Date extractExpiration(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody().getExpiration();
	}
}

