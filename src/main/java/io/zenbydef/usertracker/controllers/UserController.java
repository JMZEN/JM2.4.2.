package io.zenbydef.usertracker.controllers;

import io.zenbydef.usertracker.annotations.UserReadPermission;
import io.zenbydef.usertracker.entities.User;
import io.zenbydef.usertracker.service.UserService;
import io.zenbydef.usertracker.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @UserReadPermission
    @GetMapping("/{id}")
    public ModelAndView indexPage(@PathVariable("id") Long id) {
        return new ModelAndView("user-page", "user", userService.getUserById(id));
    }
}
