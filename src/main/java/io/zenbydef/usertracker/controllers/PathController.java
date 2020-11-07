package io.zenbydef.usertracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PathController {
    @RequestMapping("/login")
    public String list() {
        return "login/login-page";
    }

    @RequestMapping("/denied")
    public String accessDeniedPage() {
        return "status/access-denied";
    }
}
