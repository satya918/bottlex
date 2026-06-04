package com.bottelx.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bottelx.dto.CompanyDto;
import com.bottelx.dto.CreateUserRequest;
import com.bottelx.dto.PermissionDto;
import com.bottelx.dto.PermissionMatrixDto;
import com.bottelx.dto.RoleDto;
import com.bottelx.dto.UserDto;
import com.bottelx.entity.Permission;
import com.bottelx.entity.Role;
import com.bottelx.entity.User;
import com.bottelx.repository.PermissionRepository;
import com.bottelx.repository.RoleRepository;
import com.bottelx.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminUserService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
       private PermissionRepository permissionRepository;

        public Page<UserDto> getUsers(
                        int page,
                        int size) {

                return userRepository
                                .findAll(PageRequest.of(page, size))
                                .map(this::mapToDto);
        }

        public Page<UserDto> getCompanyUsers(
                        UUID companyId,
                        int page,
                        int size) {

                // QUERY 1: pagination only (IDs)
                Page<UUID> idPage = userRepository.findUserIdsByCompanyId(
                                companyId,
                                PageRequest.of(page, size));

                if (idPage.isEmpty()) {
                        return Page.empty();
                }

                // QUERY 2: fetch full data
                List<User> users = userRepository.findUsersWithRolesAndPermissions(
                                idPage.getContent());

                // maintain order (VERY IMPORTANT)
                Map<UUID, User> userMap = users.stream()
                                .collect(Collectors.toMap(User::getId, u -> u));

                List<UserDto> dtos = idPage.getContent().stream()
                                .map(userMap::get)
                                .map(this::mapToDto)
                                .toList();

                return new PageImpl<>(
                                dtos,
                                idPage.getPageable(),
                                idPage.getTotalElements());
        }

        public User createUser(
                        CreateUserRequest request,
                        UUID companyId) {

                if (userRepository
                                .findByEmailIgnoreCase(
                                                request.getEmail())
                                .isPresent()) {

                        throw new RuntimeException(
                                        "Email already exists");
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

                user.setUserName(
                                request.getUserName());

                user.setActive(true);

                user.setDeleted(false);

                user.setPassword(
                                passwordEncoder.encode(
                                                request.getPassword()));

                Set<Role> roles = Set.copyOf(
                                roleRepository.findAllById(
                                                request.getRoleIds()));

                user.setRoles(roles);

                user.setCompany(
                                new com.bottelx.entity.Company());

                user.getCompany().setId(
                                companyId);

                return userRepository.save(user);
        }

        @Transactional
        public UserDto updateStatus(UUID id, boolean active) {

                User user = userRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                user.setActive(active);

                return mapToDto(userRepository.save(user));
        }

        public PermissionMatrixDto getPermissionMatrix() {

                List<Role> roles = roleRepository.findAll();

                List<Permission> permissions = permissionRepository.findAll();

                Map<String, List<String>> matrix = new HashMap<>();

                for (Role role : roles) {

                        List<String> perms = role.getPermissions()
                                        .stream()
                                        .map(Permission::getPermissionName)
                                        .toList();

                        matrix.put(role.getRoleName(), perms);
                }

                PermissionMatrixDto dto = new PermissionMatrixDto();
                dto.setRoles(roles.stream()
                                .map(Role::getRoleName)
                                .toList());

                dto.setPermissions(permissions.stream()
                                .map(Permission::getPermissionName)
                                .toList());

                dto.setRolePermissions(matrix);

                return dto;
        }

        private UserDto mapToDto(User user) {

                UserDto dto = new UserDto();

                dto.setId(user.getId());
                dto.setFirstName(user.getFirstName());
                dto.setLastName(user.getLastName());
                dto.setEmail(user.getEmail());
                dto.setUserName(user.getUserName());
                dto.setPhone(user.getPhone());

                // company
                CompanyDto companyDto = new CompanyDto();
                companyDto.setId(user.getCompany().getId());
                companyDto.setCompanyName(user.getCompany().getCompanyName());
                companyDto.setCompanyCode(user.getCompany().getCompanyCode());

                dto.setCompany(companyDto);

                // roles
                List<RoleDto> roleDtos = user.getRoles().stream().map(role -> {

                        RoleDto roleDto = new RoleDto();

                        roleDto.setId(role.getId());
                        roleDto.setRoleName(role.getRoleName());
                        roleDto.setDescription(role.getDescription());

                        // permissions
                        List<PermissionDto> permissionDtos = role.getPermissions().stream().map(permission -> {

                                PermissionDto permissionDto = new PermissionDto();

                                permissionDto.setId(permission.getId());
                                permissionDto.setPermissionName(permission.getPermissionName());
                                permissionDto.setDescription(permission.getDescription());

                                return permissionDto;

                        }).toList();

                        roleDto.setPermissions(permissionDtos);

                        return roleDto;

                }).toList();

                dto.setRoles(roleDtos);
                dto.setActive(user.isActive());

                return dto;
        }
}
