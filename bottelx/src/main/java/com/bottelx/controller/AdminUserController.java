package com.bottelx.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.bottelx.dto.CreateUserRequest;
import com.bottelx.dto.PermissionRequest;
import com.bottelx.security.CustomUserDetails;
import com.bottelx.entity.User;
import com.bottelx.repository.UserRepository;
import com.bottelx.services.AdminUserService;
import com.bottelx.services.RoleService;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

        @Autowired
        private AdminUserService adminUserService;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RoleService roleService;

        @GetMapping
        public ResponseEntity<?> getUsers(
                        Authentication authentication,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {

                CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

                User loggedInUser = userRepository.findById(principal.getId())
                                .orElseThrow();

                return ResponseEntity.ok(
                                adminUserService.getCompanyUsers(
                                                loggedInUser.getCompany().getId(),
                                                page,
                                                size));
        }

        // @GetMapping("/all")
        // public ResponseEntity<?> getUsers(
        // @RequestParam(defaultValue = "0") int page,
        // @RequestParam(defaultValue = "10") int size) {

        // return ResponseEntity.ok(
        // adminUserService.getUsers(page, size));
        // }

        @PostMapping
        public ResponseEntity<?> createUser(
                        @RequestBody CreateUserRequest request,
                        Authentication authentication) {

                CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

                User loggedInUser = userRepository.findById(principal.getId())
                                .orElseThrow();

                return ResponseEntity.ok(
                                adminUserService.createUser(
                                                request,
                                                loggedInUser.getCompany().getId()));
        }

        @PatchMapping("/{id}/status")
        public ResponseEntity<?> updateStatus(
                        @PathVariable UUID id,
                        @RequestParam boolean active) {

                return ResponseEntity.ok(
                                adminUserService.updateStatus(id, active));
        }

        @GetMapping("/permissions/matrix")
        public ResponseEntity<?> getMatrix(Authentication auth) {

                // CustomUserDetails principal = (CustomUserDetails) auth.getPrincipal();

                return ResponseEntity.ok(
                                adminUserService.getPermissionMatrix());
        }

        @PatchMapping("/roles/{roleName}/permissions")
        public ResponseEntity<?> updatePermission(
                        @PathVariable String roleName,
                        @RequestBody PermissionRequest request) {

                roleService.togglePermission(roleName, request.getPermission());

                return ResponseEntity.ok().build();
        }

        @GetMapping("/roles")
        public ResponseEntity<?> getRoles() {
                return ResponseEntity.ok(roleService.findAll());
        }
}