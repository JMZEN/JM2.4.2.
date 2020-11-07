package io.zenbydef.usertracker.dao.roledao;

import io.zenbydef.usertracker.entities.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRoleByName(String name) {
        Role role = entityManager.find(Role.class, name);
        System.out.println(role.toString());

        return role;
    }

    @Override
    public List<Role> getRoles() {
        return entityManager.createQuery("select role from Role as role", Role.class).getResultList();
    }

    @Override
    public Role getOneRole(Long id) {
        return entityManager.find(Role.class, id);
    }


}
