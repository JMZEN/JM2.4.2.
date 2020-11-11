package io.zenbydef.usertracker.dao.roledao;

import io.zenbydef.usertracker.entities.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Override
    public Role getRoleByName(String name) {
        TypedQuery<Role> userQuery = entityManager.createNamedQuery("Role.getByName", Role.class);
        userQuery.setParameter("name", name);
        return userQuery.getSingleResult();
    }

    @Override
    public List<Role> getRoles() {
        return entityManager.createNamedQuery("Role.getAllRoles", Role.class).getResultList();
    }

    @Override
    public Role getOneRole(Long id) {
        return entityManager.find(Role.class, id);
    }
}
