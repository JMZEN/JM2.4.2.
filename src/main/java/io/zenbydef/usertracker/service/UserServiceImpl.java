package io.zenbydef.usertracker.service;

import io.zenbydef.usertracker.dao.userdao.UserDao;
import io.zenbydef.usertracker.entities.Privilege;
import io.zenbydef.usertracker.entities.Role;
import io.zenbydef.usertracker.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
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
//        if (user == null) {
//            throw new RuntimeException("No user found with whis username");
//        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            for (Privilege privilege : role.getPrivileges()) {
                authorities.add(new SimpleGrantedAuthority(privilege.getNameOfPrivilege()));
            }
        }
        return authorities;
    }

//    @Override
//    public List<User> searchUsers(String theSearchName) {
//        return userDao.searchUsers(theSearchName);
//    }
}
