package io.zenbydef.usertracker.service.userservice;

import io.zenbydef.usertracker.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getUsers();

    void saveUser(User user);

    User getUserById(Long id);

    void deleteUser(Long id);

    User findUserByName(String userName);
}
