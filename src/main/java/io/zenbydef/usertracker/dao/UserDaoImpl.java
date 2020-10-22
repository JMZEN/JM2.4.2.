package io.zenbydef.usertracker.dao;

import io.zenbydef.usertracker.entities.User;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("select user from users_db as user", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteUser(int id) {
        User userForDelete = entityManager.find(User.class, id);
        entityManager.remove(userForDelete);
    }

    @Override
    public List<User> searchUsers(String theSearchName) {
        TypedQuery<User> userQuery;
        if (theSearchName != null) {
            userQuery = entityManager.createQuery("select user from users_db as user " +
                            "where lower(user.firstName) like :theUserName or " +
                            "lower(user.lastName) like :theUserName or " + "lower(user.login) like :theUserName " +
                            "order by user.lastName",
                    User.class);
            userQuery.setParameter("theUserName", '%' + theSearchName.toLowerCase() + '%');
        } else {
            userQuery = entityManager.createQuery("select user from users_db as user order by user.lastName", User.class);
        }
        return userQuery.getResultList();
    }
}
