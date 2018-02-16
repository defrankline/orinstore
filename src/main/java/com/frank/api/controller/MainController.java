package com.frank.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class MainController {

    @RequestMapping("/")
    public String main(Principal principal,Model model) {
        model.addAttribute("username",principal.getName());
        return "main";
    }
}