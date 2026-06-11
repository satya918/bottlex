package com.bottelx.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bottelx.entity.User;
import com.bottelx.repository.UserRepository;

@Service
public class CustomUserDetailsService
                implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(
                        String username)
                        throws UsernameNotFoundException {

                User user = userRepository
                                .findByUsernameOrEmailWithRoles(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                Collection<? extends GrantedAuthority> authorities = user.getRoles()
                                .stream()
                                .map(role -> new SimpleGrantedAuthority(
                                                "ROLE_" + role.getRoleName()))
                                .collect(Collectors.toSet());

                return new CustomUserDetails(
                                user.getId(),
                                user.getUserName(),
                                user.getPassword(),
                                !user.isDeleted(),
                                authorities);
        }
}