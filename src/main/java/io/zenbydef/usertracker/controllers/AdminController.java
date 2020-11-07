package io.zenbydef.usertracker.controllers;

import io.zenbydef.usertracker.annotations.UserCreatePermission;
import io.zenbydef.usertracker.annotations.UserDeletePermission;
import io.zenbydef.usertracker.annotations.UserListReadPermission;
import io.zenbydef.usertracker.annotations.UserUpdatePermission;
import io.zenbydef.usertracker.dao.roledao.RoleDao;
import io.zenbydef.usertracker.entities.Role;
import io.zenbydef.usertracker.entities.SecurityDetailUser;
import io.zenbydef.usertracker.service.roleservice.RoleService;
import io.zenbydef.usertracker.service.securitydetailuserservice.SecurityDetailUserService;
import io.zenbydef.usertracker.util.RoleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final RoleService roleService;
    private final RoleConverter roleConverter;
    private final PasswordEncoder passwordEncoder;
    private Set<String> stringRolesSet;

    @Autowired
    private void setRolesSet() {
        this.stringRolesSet = roleService
                .getRoles()
                .stream()
                .map(Role::getNameOfRole)
                .collect(Collectors.toSet());
    }

    public AdminController(SecurityDetailUserService securityDetailUserService,
                           RoleService roleService,
                           RoleConverter roleConverter,
                           PasswordEncoder passwordEncoder) {
        this.securityDetailUserService = securityDetailUserService;
        this.roleService = roleService;
        this.roleConverter = roleConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @UserListReadPermission
    @GetMapping("/list")
    public ModelAndView listUsers() {
        List<SecurityDetailUser> userList = securityDetailUserService.getUsers();
        return new ModelAndView("admindirectory/users-table", "usersForTable", userList);
    }

    @UserCreatePermission
    @GetMapping("/add")
    public ModelAndView addUser() {
        SecurityDetailUser detailUser = new SecurityDetailUser();
        ModelAndView modelAndView = new ModelAndView("admindirectory/user-form");
        modelAndView.addObject("user", detailUser);
        modelAndView.addObject("allRoles", stringRolesSet);
        return modelAndView;
    }

    @UserCreatePermission
    @PostMapping("/save")
    public ModelAndView saveUser(@ModelAttribute("user") SecurityDetailUser user,
                                 @RequestParam("roles") String[] roles) {
        user.setRoles(roleConverter.convertRoles(String.join(" ", roles)));
        user.setPassword(passwordEncoder.encode((user.getPassword())));
        securityDetailUserService.saveUser(user);
        return new ModelAndView("redirect:/admin/list");
    }

    @UserUpdatePermission
    @PostMapping("/update")
    public ModelAndView showFormForUpdate(@RequestParam("userId") Long userId) {
        SecurityDetailUser detailUser = securityDetailUserService.getUserById(userId);
        ModelAndView modelAndView = new ModelAndView("admindirectory/user-update");
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("username", detailUser.getUsername());
        modelAndView.addObject("allRoles", stringRolesSet);
        return modelAndView;
    }

    @UserCreatePermission
    @PostMapping("/saveupduser")
    public ModelAndView saveUpdatedUser(@RequestParam("userId") Long userId,
                                        @RequestParam("username") String username,
                                        @RequestParam("roles") String[] roles) {
        SecurityDetailUser detailUser = securityDetailUserService.getUserById(userId);
        detailUser.setUsername(username);
        detailUser.setRoles(roleConverter.convertRoles(String.join(" ", roles)));
        securityDetailUserService.saveUser(detailUser);
        return new ModelAndView("redirect:/admin/list");
    }

    @UserUpdatePermission
    @GetMapping("/updatepass")
    public ModelAndView updatePassword(@RequestParam("userId") Long userId) {
        ModelAndView modelAndView = new ModelAndView("admindirectory/user-change-pass");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @UserUpdatePermission
    @PostMapping("/savepass")
    public ModelAndView savePassword(@RequestParam("userId") Long userId, @RequestParam("pass") String password) {
        SecurityDetailUser detailUser = securityDetailUserService.getUserById(userId);
        detailUser.setPassword(passwordEncoder.encode((password)));
        securityDetailUserService.saveUser(detailUser);
        return new ModelAndView("redirect:/admin/list");
    }

    @UserDeletePermission
    @PostMapping("/delete")
    public ModelAndView deleteUser(@RequestParam("userId") Long userId) {
        securityDetailUserService.deleteUser(userId);
        return new ModelAndView("redirect:/admin/list");
    }
}
