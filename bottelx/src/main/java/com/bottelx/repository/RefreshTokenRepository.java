package com.bottelx.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bottelx.entity.RefreshToken;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

	Optional<RefreshToken> findByIdAndRevokedFalse(UUID id);
	long countByUser_IdAndRevokedFalse(UUID id);
	
	@Modifying
    @Query("UPDATE RefreshToken t SET t.revoked = true WHERE t.user.id = :userId")
    void revokeAllByUser(@Param("userId") UUID userId);
	
	
	@Query("""
			   SELECT t FROM RefreshToken t
			   WHERE t.user.id = :userId
			     AND t.revoked = false
			     AND t.expiry > CURRENT_TIMESTAMP
			""")
			List<RefreshToken> findActiveSessions(UUID userId);
//	void revokeOldestSessions(UUID id, long l);


}

