package io.zenbydef.usertracker.util;

import io.zenbydef.usertracker.entities.Role;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class RoleConverter {
    public Set<Role> convertRoles(String[] roles, Set<Role> rolesSet) {
        Set<Role> convertedRoleSet = new HashSet<>();
        for (String s : roles) {
            if (!s.equals("")) {
                Role roleToFind = getRole(s, rolesSet);
                convertedRoleSet.add(roleToFind);
            }
        }
        return convertedRoleSet;
    }

    private Role getRole(String s, Set<Role> rolesSet) {
        Role roleToFind = new Role();
        for (Role role : rolesSet) {
            if (s.equalsIgnoreCase(role.getNameOfRole())) {
                roleToFind = role;
            }
        }
        return roleToFind;
    }
}
