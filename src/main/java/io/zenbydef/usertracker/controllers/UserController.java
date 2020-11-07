package io.zenbydef.usertracker.controllers;

import io.zenbydef.usertracker.annotations.UserViewProfilePermission;
import io.zenbydef.usertracker.entities.SecurityDetailUser;
import io.zenbydef.usertracker.service.securitydetailuserservice.SecurityDetailUserService;
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
    private SecurityDetailUserService securityDetailUserService;

    @UserViewProfilePermission
    @GetMapping("/user")
    public ModelAndView indexPage(Principal principal) {
        SecurityDetailUser user = securityDetailUserService.findUserByName(principal.getName());
        return new ModelAndView("userdirectory/user-page", "user", user);
    }
}
