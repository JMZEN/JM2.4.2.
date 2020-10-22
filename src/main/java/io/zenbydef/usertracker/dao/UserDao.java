package io.zenbydef.usertracker.dao;

import io.zenbydef.usertracker.entities.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();

    void saveUser(User user);

    User getUserById(int id);

    void deleteUser(int id);

    List<User> searchUsers(String theSearchName);
}
