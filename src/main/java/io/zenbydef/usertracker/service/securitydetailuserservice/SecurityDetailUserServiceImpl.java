package io.zenbydef.usertracker.service.securitydetailuserservice;

import io.zenbydef.usertracker.dao.securitydetailuserdao.SecurityDetailUserDao;
import io.zenbydef.usertracker.entities.SecurityDetailUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SecurityDetailUserServiceImpl implements SecurityDetailUserService {
    private final SecurityDetailUserDao userDao;

    public SecurityDetailUserServiceImpl(SecurityDetailUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<SecurityDetailUser> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public void saveUser(SecurityDetailUser user) {
        userDao.saveUser(user);
    }

    @Override
    public SecurityDetailUser getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public SecurityDetailUser findUserByName(String userName) {
        return userDao.findUserByName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final SecurityDetailUser user = userDao.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with this username: " + username);
        }
        return user;
    }
}
