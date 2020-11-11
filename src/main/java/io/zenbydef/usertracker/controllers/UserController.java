package io.zenbydef.usertracker.controllers;

import io.zenbydef.usertracker.annotations.UserViewProfilePermission;
import io.zenbydef.usertracker.entities.User;
import io.zenbydef.usertracker.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @UserViewProfilePermission
    @GetMapping("/user")
    public ModelAndView indexPage(Principal principal) {
        User user = userService.findUserByName(principal.getName());
        return new ModelAndView("userdirectory/user-page", "user", user);
    }
}
