package com.frank.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/auth")
    public String auth() {
        return "auth";
    }

    @RequestMapping("/")
    public String main() {
        return "main";
    }
}