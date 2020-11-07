package io.zenbydef.usertracker.service.roleservice;

import io.zenbydef.usertracker.entities.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();

    Role getOneRole(Long id);

    Role getRoleByName(String name);
}
