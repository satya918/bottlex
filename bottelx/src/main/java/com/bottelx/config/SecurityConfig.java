package com.bottelx.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.bottelx.security.JwtAuthFilter1;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter1 jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                /* ================= CORS ================= */
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                /* ================= CSRF ================= */
                .csrf(csrf -> csrf.disable()) // OK for pure JWT APIs

                /* ================= SECURITY HEADERS ================= */
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'"))
                        .frameOptions(frame -> frame.deny())
                        .xssProtection(xss -> xss.disable())
                        .contentTypeOptions(cto -> {
                        })
                        .referrerPolicy(ref -> ref.policy(
                                org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER)))

                /* ================= AUTHORIZATION ================= */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/register",
                                "/api/auth/refresh",
                                "/api/public/**")
                        .permitAll()

                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        .requestMatchers(
                                "/api/admin/**")
                        .hasAnyRole(
                                "SUPER_ADMIN",
                                "COMPANY_ADMIN")

                        .anyRequest().authenticated())

                /* ================= STATELESS ================= */
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                /* ================= JWT FILTER ================= */
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                /* ================= AUTH ERROR HANDLING ================= */
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                (req, res, ex1) -> {
                                    res.setStatus(401);
                                    res.setContentType("application/json");
                                    res.getWriter().write(
                                            "{\"error\":\"Unauthorized\",\"message\":\"Invalid or expired token\"}");
                                })
                        .accessDeniedHandler(
                                (req, res, ex2) -> {
                                    res.setStatus(403);
                                    res.setContentType("application/json");
                                    res.getWriter().write(
                                            "{\"error\":\"Forbidden\",\"message\":\"Access denied\"}");
                                }));

        return http.build();
    }

    /* ================= CORS CONFIG ================= */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://localhost:8081",
                "https://bottlex-1.onrender.com",
                "https://bottlex-d7bzxg28p-satya918s-projects.vercel.app",
                "http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /* ================= PASSWORD ENCODER ================= */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /* ================= AUTH MANAGER ================= */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
