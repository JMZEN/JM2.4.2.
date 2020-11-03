package io.zenbydef.usertracker.controllers;

import io.zenbydef.usertracker.annotations.UserCreatePermission;
import io.zenbydef.usertracker.annotations.UserDeletePermission;
import io.zenbydef.usertracker.annotations.UserListReadPermission;
import io.zenbydef.usertracker.annotations.UserUpdatePermission;
import io.zenbydef.usertracker.entities.SecurityDetailUser;
import io.zenbydef.usertracker.service.SecurityDetailUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/admin")
public class AdminController {

    private final SecurityDetailUserService securityDetailUserService;

    public AdminController(SecurityDetailUserService securityDetailUserService) {
        this.securityDetailUserService = securityDetailUserService;
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
        return new ModelAndView("user-form", "user", new SecurityDetailUser());
    }

    @UserCreatePermission
    @PostMapping("/saveuser")
    public ModelAndView saveUser(@ModelAttribute("user") SecurityDetailUser user) {
        securityDetailUserService.saveUser(user);
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
}
