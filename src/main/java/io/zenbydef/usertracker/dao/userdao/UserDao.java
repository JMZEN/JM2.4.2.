package io.zenbydef.usertracker.dao.userdao;

import io.zenbydef.usertracker.entities.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();

    void saveUser(User user);

    User getUserById(Long id);

    void deleteUser(Long id);

//    List<User> searchUsers(String theSearchName);

    User findUserByName(String userName);
}
