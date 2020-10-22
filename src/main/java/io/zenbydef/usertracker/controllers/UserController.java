package io.zenbydef.usertracker.controllers;

import io.zenbydef.usertracker.entities.User;
import io.zenbydef.usertracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ModelAndView listUsers() {
        List<User> userList = userService.getUsers();
        return new ModelAndView("users-table", "usersForTable", userList);
    }

    @GetMapping("/adduser")
    public ModelAndView addUser() {
        return new ModelAndView("user-form", "user", new User());
    }

    @PostMapping("/saveuser")
    public ModelAndView saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return new ModelAndView("redirect:/users/list");
    }

    @PostMapping("/updateuser")
    public ModelAndView showFormForUpdate(@RequestParam("userId") int userId) {
        return new ModelAndView("user-form", "user", userService.getUserById(userId));
    }

    @PostMapping("/deleteuser")
    public ModelAndView deleteUser(@RequestParam("userId") int userId) {
        userService.deleteUser(userId);
        return new ModelAndView("redirect:/users/list");
    }

    @GetMapping("/searchuser")
    public ModelAndView searchUser(@RequestParam("theSearchName") String theSearchName) {
        List<User> userList;
        if (theSearchName == null) {
            userList = new ArrayList<>();
        } else {
            userList = userService.searchUsers(theSearchName);
        }
        return new ModelAndView("user-search-table", "users", userList);
    }
}
