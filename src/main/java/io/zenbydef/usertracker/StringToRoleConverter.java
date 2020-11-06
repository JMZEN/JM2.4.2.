package io.zenbydef.usertracker;

import io.zenbydef.usertracker.dao.RoleDao;
import io.zenbydef.usertracker.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRoleConverter implements Converter<Object, Role> {
    @Autowired
    private RoleDao roleDao;

    @Override
    public Role convert(Object o) {
        Long id = Long.parseLong((String) o);
        Role role = roleDao.getOneRole(id);
        System.out.println("Role: " + role);
        return role;
    }
}
