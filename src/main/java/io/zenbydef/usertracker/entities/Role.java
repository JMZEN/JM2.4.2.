package io.zenbydef.usertracker.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id")
    private Long id;

    @Column(name = "name")
    private String nameOfRole;

    @ManyToMany(mappedBy = "roles")
    private Set<SecurityDetailUser> users;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id"))
    private Collection<Privilege> privileges;

    public Role() {
    }

    public Role(Long id, String nameOfRole) {
        this.id = id;
        this.nameOfRole = nameOfRole;
    }

    public Role(String nameOfRole) {
        this.nameOfRole = nameOfRole;
    }

    public Long getId() {
        return id;
    }

    public String getNameOfRole() {
        return nameOfRole;
    }

    public void setNameOfRole(String nameOfRole) {
        this.nameOfRole = nameOfRole;
    }

    public Set<SecurityDetailUser> getUsers() {
        return users;
    }

    public void setUsers(Set<SecurityDetailUser> users) {
        this.users = users;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != null ? !id.equals(role.id) : role.id != null) return false;
        if (nameOfRole != null ? !nameOfRole.equals(role.nameOfRole) : role.nameOfRole != null) return false;
        if (users != null ? !users.equals(role.users) : role.users != null) return false;
        return privileges != null ? privileges.equals(role.privileges) : role.privileges == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nameOfRole != null ? nameOfRole.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (privileges != null ? privileges.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.nameOfRole;
    }
}
