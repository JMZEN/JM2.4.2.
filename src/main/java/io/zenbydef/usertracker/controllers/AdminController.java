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
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
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

    private List<String> rolesList;

    @Autowired
    private void setRolesSet() {
        List<String> list = new ArrayList<>();
        for (Role role : roleDao.getRoles()) {
            String nameOfRole = role.getNameOfRole();
            list.add(nameOfRole);
        }
        this.rolesList = list;
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
    @GetMapping("/adduser")
    public ModelAndView addUser() {
        SecurityDetailUser detailUser = new SecurityDetailUser();

        ModelAndView modelAndView = new ModelAndView("user-form");
        modelAndView.addObject("user", detailUser);
        modelAndView.addObject("allRoles", rolesList);
        return modelAndView;
    }

    @UserCreatePermission
    @PostMapping("/saveuser")
    public ModelAndView saveUser(@ModelAttribute("user") SecurityDetailUser user, @RequestParam("roles") String[] roles) {
        securityDetailUserService.saveUser(saveUserAsUser(user.getPassword(), user.getUsername(), String.join(" ", roles)));
        return new ModelAndView("redirect:/admin/list");
    }

    @UserUpdatePermission
    @PostMapping("/updateuser")
    public ModelAndView showFormForUpdate(@RequestParam("userId") Long userId) {
        return new ModelAndView("user-form", "user", securityDetailUserService.getUserById(userId));
    }

    @UserDeletePermission
    @PostMapping("/deleteuser")
    public ModelAndView deleteUser(@RequestParam("userId") Long userId) {
        securityDetailUserService.deleteUser(userId);
        return new ModelAndView("redirect:/admin/list");
    }

    private SecurityDetailUser saveUserAsUser(String password,
                                              String username,
                                              String roles) {
        Set<Role> set = new HashSet<>();

        for (String s : roles.split(" ")) {
            Role role = new Role(s);
            Role roleToFind = new Role();
            for (String role1 : rolesList) {
                if (role1.equals(s)) {
                    System.out.println("role find");
                }
            }

            for (Role role1 : roleDao.getRoles()) {
                if (role.getNameOfRole().equalsIgnoreCase(role1.getNameOfRole())) {
                    System.out.println("role find");
                    roleToFind = role1;
                }

            }

            System.out.println(role.toString());
            System.out.println(roleToFind.toString());
//            if (role.getNameOfRole().equalsIgnoreCase(roleToFind.getNameOfRole())) {
            set.add(roleToFind);
//            } else {
//                set.add(role);
//            }
        }
        SecurityDetailUser user = new SecurityDetailUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(set);
        return user;
    }
}
