package io.zenbydef.usertracker.dao.securitydetailuserdao;

import io.zenbydef.usertracker.entities.SecurityDetailUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class SecurityDetailUserDaoImpl implements SecurityDetailUserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SecurityDetailUser> getUsers() {
        return entityManager.createQuery("select user from SecurityDetailUser as user", SecurityDetailUser.class).getResultList();
    }

    @Override
    public void saveUser(SecurityDetailUser user) {
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
