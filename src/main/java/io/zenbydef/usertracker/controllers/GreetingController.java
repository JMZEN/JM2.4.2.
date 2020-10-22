package io.zenbydef.usertracker.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreetingController {

    @RequestMapping("/")
    public String indexPage() {
        return "redirect:/users/list";
    }
}
