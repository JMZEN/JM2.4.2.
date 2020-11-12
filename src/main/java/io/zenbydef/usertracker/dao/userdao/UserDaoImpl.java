package io.zenbydef.usertracker.dao.userdao;

import io.zenbydef.usertracker.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        return entityManager.createNamedQuery("User.getUsers", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
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
        TypedQuery<User> query = entityManager.createNamedQuery("User.findUserByName", User.class);
        query.setParameter("theUserName", userName.toLowerCase());
        return query.getSingleResult();
    }
}
