package io.zenbydef.usertracker.util;

import io.zenbydef.usertracker.entities.Role;
import io.zenbydef.usertracker.service.roleservice.RoleService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class RoleConverter {
    private final RoleService roleService;

    public RoleConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    public Collection<Role> convertRoles(String roles) {
        Set<Role> convertedRoleSet = new HashSet<>();
        Role roleToFind = new Role();
        for (String s : roles.split(" ")) {
            roleToFind = getRole(s, roleToFind);
            convertedRoleSet.add(roleToFind);
        }
        return convertedRoleSet;
    }

    private Role getRole(String s, Role roleToFind) {
        for (Role role1 : roleService.getRoles()) {
            if (s.equalsIgnoreCase(role1.getNameOfRole())) {
                roleToFind = role1;
            }
        }
        return roleToFind;
    }
}
