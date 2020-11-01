package io.zenbydef.usertracker.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "privileges")
public class Privilege {

    @Id
    @Column(name = "privilege_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String nameOfPrivilege;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege() {
    }

    public Privilege(String nameOfPrivilege) {
        this.nameOfPrivilege = nameOfPrivilege;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfPrivilege() {
        return nameOfPrivilege;
    }

    public void setNameOfPrivilege(String nameOfPrivilege) {
        this.nameOfPrivilege = nameOfPrivilege;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
