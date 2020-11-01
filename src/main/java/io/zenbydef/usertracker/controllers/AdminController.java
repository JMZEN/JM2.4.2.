package io.zenbydef.usertracker.controllers;

import io.zenbydef.usertracker.annotations.UserCreatePermission;
import io.zenbydef.usertracker.annotations.UserDeletePermission;
import io.zenbydef.usertracker.annotations.UserListReadPermission;
import io.zenbydef.usertracker.annotations.UserUpdatePermission;
import io.zenbydef.usertracker.entities.User;
import io.zenbydef.usertracker.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @UserListReadPermission
    @GetMapping("/list")
    public ModelAndView listUsers() {
        List<User> userList = userService.getUsers();
        return new ModelAndView("users-table", "usersForTable", userList);
    }

    @UserCreatePermission
    @GetMapping("/adduser")
    public ModelAndView addUser() {
        return new ModelAndView("user-form", "user", new User());
    }

    @UserCreatePermission
    @PostMapping("/saveuser")
    public ModelAndView saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return new ModelAndView("redirect:/admin/list");
    }

    @UserUpdatePermission
    @PostMapping("/updateuser")
    public ModelAndView showFormForUpdate(@RequestParam("userId") Long userId) {
        return new ModelAndView("user-form", "user", userService.getUserById(userId));
    }

    @UserDeletePermission
    @PostMapping("/deleteuser")
    public ModelAndView deleteUser(@RequestParam("userId") Long userId) {
        userService.deleteUser(userId);
        return new ModelAndView("redirect:/admin/list");
    }


//    @GetMapping("/searchuser")
//    public ModelAndView searchUser(@RequestParam("theSearchName") String theSearchName) {
//        List<User> userList;
//        if (theSearchName == null) {
//            userList = new ArrayList<>();
//        } else {
//            userList = userService.searchUsers(theSearchName);
//        }
//        return new ModelAndView("user-search-table", "users", userList);
//    }
}
