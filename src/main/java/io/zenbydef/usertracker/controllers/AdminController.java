package io.zenbydef.usertracker.controllers;

import io.zenbydef.usertracker.annotations.*;
import io.zenbydef.usertracker.entities.User;
import io.zenbydef.usertracker.service.userservice.UserService;
import io.zenbydef.usertracker.util.RoleManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.*;

@Controller
@Transactional
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleManager roleManager;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService,
                           RoleManager roleManager,
                           PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleManager = roleManager;
        this.passwordEncoder = passwordEncoder;
    }

    @UserListReadPermission
    @GetMapping("/list")
    public ModelAndView listUsers() {
        List<User> userList = userService.getUsers();
        return new ModelAndView("admindirectory/users-table", "usersForTable", userList);
    }

    @UserViewProfilePermission
    @GetMapping("user")
    public ModelAndView getUserProfile(@RequestParam("userId") Long userId) {
        User detailUser = userService.getUserById(userId);
        Collection<String> roles = detailUser.getRolesAsStrings();
        ModelAndView modelAndView = new ModelAndView("userdirectory/user-page");
        modelAndView.addObject("user", detailUser);
        modelAndView.addObject("userRoles", roles);
        return modelAndView;
    }

    @UserCreatePermission
    @GetMapping("/add")
    public ModelAndView addUser() {
        User detailUser = new User();
        ModelAndView modelAndView = new ModelAndView("admindirectory/user-form");
        modelAndView.addObject("user", detailUser);
        modelAndView.addObject("allRoles", roleManager.getStringRoles());
        return modelAndView;
    }

    @UserCreatePermission
    @PostMapping("/add")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("roles") String[] roles) {
        user.setRoles(roleManager.convertRoles(roles));
        user.setPassword(passwordEncoder.encode((user.getPassword())));
        userService.saveUser(user);
        return "redirect:/admin/list";
    }

    @UserUpdatePermission
    @GetMapping("/update")
    public ModelAndView showFormForUpdate(@RequestParam("userId") Long userId) {
        User detailUser = userService.getUserById(userId);
        ModelAndView modelAndView = new ModelAndView("admindirectory/user-update");
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("username", detailUser.getUsername());
        modelAndView.addObject("allRoles", roleManager.getStringRoles());
        return modelAndView;
    }

    @UserCreatePermission
    @PostMapping("/update")
    public String saveUpdatedUser(@RequestParam("userId") Long userId,
                                  @RequestParam("username") String username,
                                  @RequestParam("roles") String[] roles) {
        User detailUser = userService.getUserById(userId);
        detailUser.setUsername(username);
        detailUser.setRoles(roleManager.convertRoles(roles));
        userService.saveUser(detailUser);
        return "redirect:/admin/list";
    }

    @UserUpdatePermission
    @GetMapping("/updatepass")
    public ModelAndView updatePassword(@RequestParam("userId") Long userId) {
        ModelAndView modelAndView = new ModelAndView("admindirectory/user-change-pass");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @UserUpdatePermission
    @PostMapping("/updatepass")
    public String savePassword(@RequestParam("userId") Long userId, @RequestParam("pass") String password) {
        User detailUser = userService.getUserById(userId);
        detailUser.setPassword(passwordEncoder.encode((password)));
        userService.saveUser(detailUser);
        return "redirect:/admin/list";
    }

    @UserDeletePermission
    @PostMapping("/delete")
    public ModelAndView deleteUser(@RequestParam("userId") Long userId) {
        userService.deleteUser(userId);
        return new ModelAndView("redirect:/admin/list");
    }
}
