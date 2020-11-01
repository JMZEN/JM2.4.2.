package io.zenbydef.usertracker.dao.userdao;

import io.zenbydef.usertracker.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final PasswordEncoder passwordEncoder;

    public UserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("select user from users as user", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode((user.getPassword())));
        entityManager.merge(user);
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteUser(Long id) {
        User userForDelete = entityManager.find(User.class, id);
        entityManager.remove(userForDelete);
    }

    @Override
    public User findUserByName(String userName) {
        TypedQuery<User> query = entityManager.createQuery("select user from users as user where lower(user.username) like :theUserName ", User.class);
        query.setParameter("theUserName", '%' + userName.toLowerCase() + '%');

        return query.getSingleResult();
    }
}
