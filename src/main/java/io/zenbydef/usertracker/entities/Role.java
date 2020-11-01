package io.zenbydef.usertracker.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String nameOfRole;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH},
            fetch = FetchType.EAGER)
    @JoinTable(name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id"))
    private Collection<Privilege> privileges;

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public String getNameOfRole() {
        return nameOfRole;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }
}
