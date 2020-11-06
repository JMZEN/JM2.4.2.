package io.zenbydef.usertracker.dao;

import io.zenbydef.usertracker.entities.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getRoles();

    Role getOneRole(Long id);

    Role getRoleByName(String name);
}
