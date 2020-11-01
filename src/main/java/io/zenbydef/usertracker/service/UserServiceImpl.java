package io.zenbydef.usertracker.service;

import io.zenbydef.usertracker.dao.userdao.UserDao;
import io.zenbydef.usertracker.entities.SecurityDetailUser;
import io.zenbydef.usertracker.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public User findUserByName(String userName) {
        return userDao.findUserByName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userDao.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with this username: " + username);
        }
        return new SecurityDetailUser(user);
    }
}
