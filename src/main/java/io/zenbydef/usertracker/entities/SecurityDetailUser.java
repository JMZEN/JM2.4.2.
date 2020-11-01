//package io.zenbydef.usertracker.entities;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
//public class SecurityDetailUser extends User implements UserDetails {
//
//    private final User user;
//
//    public SecurityDetailUser(User user) {
//        this.user = user;
//    }
//
////    @Override
////    public Collection<? extends GrantedAuthority> getAuthorities() {
////        Set<Privilege> set = new HashSet<>();
////        for (Role role : user.getRoles()) {
////            Collection<Privilege> rolePrivileges = role.getPrivileges();
////            set.addAll(rolePrivileges);
////        }
////        return set;
////    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
