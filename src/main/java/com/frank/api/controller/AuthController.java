package com.frank.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    @RequestMapping("/auth")
    public String auth() {
        return "auth";
    }
}