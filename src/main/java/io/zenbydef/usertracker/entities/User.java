package io.zenbydef.usertracker.entities;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Table
@Component
@Entity(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;

    @Transient
    private Set<Privilege> authorities;

    public Set<Privilege> getAuthorities() {
        Set<Privilege> set = new HashSet<>();
        for (Role role : this.roles) {
            Collection<Privilege> rolePrivileges = role.getPrivileges();
            set.addAll(rolePrivileges);
        }
        return set;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
