package io.zenbydef.usertracker.dao.securitydetailuserdao;

import io.zenbydef.usertracker.entities.SecurityDetailUser;

import java.util.List;

public interface SecurityDetailUserDao {
    List<SecurityDetailUser> getUsers();

    void saveUser(SecurityDetailUser user);

    SecurityDetailUser getUserById(Long id);

    void deleteUser(Long id);

    SecurityDetailUser findUserByName(String userName);
}
