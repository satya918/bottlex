package com.bottelx.services;

import com.bottelx.dto.RegisterRequest;
import com.bottelx.entity.Role;
import com.bottelx.entity.User;
import com.bottelx.repository.RoleRepository;
import com.bottelx.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private RoleRepository roleRepository;

        // REGISTER

        public String register(RegisterRequest request) {

                // EMAIL CHECK

                if (userRepository
                                .findByEmailIgnoreCase(request.getEmail())
                                .isPresent()) {

                        return "Email already exists";
                }

                // USERNAME CHECK

                if (userRepository
                                .findByUserNameIgnoreCase(request.getEmail())
                                .isPresent()) {

                        return "Username already exists";
                }

                User user = new User();

                user.setFirstName(
                                request.getFirstName());

                user.setLastName(
                                request.getLastName());

                user.setEmail(
                                request.getEmail());

                user.setPhone(
                                request.getPhone());

                user.setProfileImage(
                                request.getProfileImage());

                // USERNAME

                user.setUserName(
                                request.getEmail());

                // DEFAULT VALUES

                user.setWalletBalance(
                                BigDecimal.ZERO);

                user.setDeleted(false);
                Role userRole = roleRepository
                                .findByRoleName("USER")
                                .orElseThrow(() -> new RuntimeException("Role not found"));

                user.setRoles(Set.of(userRole));
                user.setUpiId(null);

                // ENCRYPT PASSWORD

                user.setPassword(
                                passwordEncoder.encode(
                                                request.getPassword()));

                userRepository.save(user);

                return "Registration Successful";
        }
}