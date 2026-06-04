package com.bottelx.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottelx.entity.Permission;
import com.bottelx.entity.Role;
import com.bottelx.repository.PermissionRepository;
import com.bottelx.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional
    public void togglePermission(String roleName, String permissionName) {

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow();

        Permission permission = permissionRepository.findByPermissionName(permissionName)
                .orElseThrow();

        if (role.getPermissions().contains(permission)) {
            role.getPermissions().remove(permission);
        } else {
            role.getPermissions().add(permission);
        }

        roleRepository.save(role);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

}
