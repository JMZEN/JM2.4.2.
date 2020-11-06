package io.zenbydef.usertracker.controllers;

import io.zenbydef.usertracker.annotations.UserCreatePermission;
import io.zenbydef.usertracker.annotations.UserDeletePermission;
import io.zenbydef.usertracker.annotations.UserListReadPermission;
import io.zenbydef.usertracker.annotations.UserUpdatePermission;
import io.zenbydef.usertracker.dao.RoleDao;
import io.zenbydef.usertracker.entities.Role;
import io.zenbydef.usertracker.entities.SecurityDetailUser;
import io.zenbydef.usertracker.service.SecurityDetailUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Transactional
@RequestMapping("/admin")
public class AdminController {

    private final SecurityDetailUserService securityDetailUserService;
    private final RoleDao roleDao;

    private Set<String> rolesSet;

    @Autowired
    private void setRolesSet() {
        this.rolesSet = roleDao.getRoles().stream().map(Role::getNameOfRole).collect(Collectors.toSet());
    }

    public AdminController(SecurityDetailUserService securityDetailUserService, RoleDao roleDao) {
        this.securityDetailUserService = securityDetailUserService;
        this.roleDao = roleDao;
    }

    @UserListReadPermission
    @GetMapping("/list")
    public ModelAndView listUsers() {
        List<SecurityDetailUser> userList = securityDetailUserService.getUsers();
        return new ModelAndView("users-table", "usersForTable", userList);
    }

    @UserCreatePermission
    @GetMapping("/add")
    public ModelAndView addUser() {
        SecurityDetailUser detailUser = new SecurityDetailUser();
        ModelAndView modelAndView = new ModelAndView("user-form");
        modelAndView.addObject("user", detailUser);
        modelAndView.addObject("allRoles", rolesSet);
        return modelAndView;
    }

    @UserCreatePermission
    @PostMapping("/save")
    public ModelAndView saveUser(@ModelAttribute("user") SecurityDetailUser user, @RequestParam("roles") String[] roles) {
        user.setRoles(convertRoles(String.join(" ", roles)));
        securityDetailUserService.saveUser(user);
        return new ModelAndView("redirect:/admin/list");
    }

    @UserUpdatePermission
    @PostMapping("/update")
    public ModelAndView showFormForUpdate(@RequestParam("userId") Long userId) {
        SecurityDetailUser detailUser = securityDetailUserService.getUserById(userId);
        ModelAndView modelAndView = new ModelAndView("user-form");
        modelAndView.addObject("user", detailUser);
        modelAndView.addObject("allRoles", rolesSet);
        return modelAndView;
    }

    @UserDeletePermission
    @PostMapping("/delete")
    public ModelAndView deleteUser(@RequestParam("userId") Long userId) {
        securityDetailUserService.deleteUser(userId);
        return new ModelAndView("redirect:/admin/list");
    }

    private Collection<Role> convertRoles(String roles) {
        Set<Role> convertedRoleSet = new HashSet<>();
        Role roleToFind = new Role();
        for (String s : roles.split(" ")) {
            roleToFind = getRole(s, roleToFind);
            convertedRoleSet.add(roleToFind);
        }
        return convertedRoleSet;
    }

    private Role getRole(String s, Role roleToFind) {
        for (Role role1 : roleDao.getRoles()) {
            if (s.equalsIgnoreCase(role1.getNameOfRole())) {
                roleToFind = role1;
            }
        }
        return roleToFind;
    }
}
