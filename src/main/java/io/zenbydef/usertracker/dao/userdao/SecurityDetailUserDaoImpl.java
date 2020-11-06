package io.zenbydef.usertracker.dao.userdao;

import io.zenbydef.usertracker.dao.RoleDao;
import io.zenbydef.usertracker.entities.Role;
import io.zenbydef.usertracker.entities.SecurityDetailUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class SecurityDetailUserDaoImpl implements SecurityDetailUserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RoleDao roleDao;

    private final PasswordEncoder passwordEncoder;

    public SecurityDetailUserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<SecurityDetailUser> getUsers() {
        return entityManager.createQuery("select user from SecurityDetailUser as user", SecurityDetailUser.class).getResultList();
    }

    @Override
    public void saveUser(SecurityDetailUser user) {
        user.setPassword(passwordEncoder.encode((user.getPassword())));
        entityManager.merge(user);
    }

    @Override
    public SecurityDetailUser getUserById(Long id) {
        return entityManager.find(SecurityDetailUser.class, id);
    }

    @Override
    public void deleteUser(Long id) {
        SecurityDetailUser userForDelete = entityManager.find(SecurityDetailUser.class, id);
        entityManager.remove(userForDelete);
    }

    @Override
    public SecurityDetailUser findUserByName(String userName) {
        TypedQuery<SecurityDetailUser> query = entityManager.createQuery("select user from SecurityDetailUser as user where lower(user.username) like :theUserName ", SecurityDetailUser.class);
        query.setParameter("theUserName", '%' + userName.toLowerCase() + '%');

        return query.getSingleResult();
    }
}