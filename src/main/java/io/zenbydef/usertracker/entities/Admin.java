//package io.zenbydef.usertracker.entities;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.persistence.*;
//
//@Table
//@Component
//@Entity(name = "admins")
//public class Admin {
//    @Id
//    @Column(name = "admin_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "admin_first_name")
//    private String firstName;
//
//    @Column(name = "admin_last_name")
//    private String lastName;
//
//    @Column(name = "admin_username")
//    private String UserName;
//
//    @Autowired
//    @OneToOne(mappedBy = "administrator", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private SecurityDetailUser securityDetailUser;
//
//    public Admin() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getUserName() {
//        return UserName;
//    }
//
//    public void setUserName(String userName) {
//        UserName = userName;
//    }
//
//    public SecurityDetailUser getSecurityDetailUser() {
//        return securityDetailUser;
//    }
//
//    public void setSecurityDetailUser(SecurityDetailUser securityDetailUser) {
//        this.securityDetailUser = securityDetailUser;
//    }
//}
