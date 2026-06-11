package com.bottelx.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bottelx.entity.User;

public interface UserRepository
                extends JpaRepository<User, UUID> {

        Optional<User> findByPhone(String phone);

        Optional<User> findByEmail(
                        String email);

        Optional<User> findByUserNameIgnoreCaseOrEmailIgnoreCase(
                        String userName,
                        String email);

        Optional<User> findByEmailIgnoreCase(String email);

        Optional<User> findByUserNameIgnoreCase(String userName);

        @Query("""
                            SELECT DISTINCT u
                            FROM User u
                            LEFT JOIN FETCH u.company
                            LEFT JOIN FETCH u.roles r
                            LEFT JOIN FETCH r.permissions
                            WHERE u.company.id = :companyId
                        """)
        Page<User> findByCompanyId(
                        UUID companyId,
                        Pageable pageable);

        @Query("""
                        SELECT u.id
                        FROM User u
                        WHERE u.company.id = :companyId
                        ORDER BY u.createdAt DESC
                        """)
        Page<UUID> findUserIdsByCompanyId(
                        @Param("companyId") UUID companyId,
                        Pageable pageable);

        @Query("""
                        SELECT DISTINCT u
                        FROM User u
                        LEFT JOIN FETCH u.roles r
                        LEFT JOIN FETCH r.permissions
                        LEFT JOIN FETCH u.company
                        WHERE u.id IN :ids
                        """)
        List<User> findUsersWithRolesAndPermissions(
                        @Param("ids") List<UUID> ids);

        Page<User> findAll(Pageable pageable);

        @Query("""
                            SELECT DISTINCT u
                            FROM User u
                            LEFT JOIN FETCH u.roles
                            WHERE LOWER(u.userName) = LOWER(:username)
                               OR LOWER(u.email) = LOWER(:username)
                        """)
        Optional<User> findByUsernameOrEmailWithRoles(
                        @Param("username") String username);

}