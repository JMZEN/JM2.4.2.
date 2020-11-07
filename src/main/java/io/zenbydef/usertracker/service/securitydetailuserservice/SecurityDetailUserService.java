package io.zenbydef.usertracker.service.securitydetailuserservice;

import io.zenbydef.usertracker.entities.SecurityDetailUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface SecurityDetailUserService extends UserDetailsService {
    List<SecurityDetailUser> getUsers();

    void saveUser(SecurityDetailUser user);

    SecurityDetailUser getUserById(Long id);

    void deleteUser(Long id);

    SecurityDetailUser findUserByName(String userName);
}
