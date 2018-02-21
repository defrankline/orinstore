package com.frank.api.controller;

import com.frank.api.model.auth.User;
import com.frank.api.service.setup.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;

@Controller
public class MainController {

    @Autowired
    private CountryService countryService;

    @RequestMapping("/")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public String main(Principal principal,Model model) {
        model.addAttribute("username",principal.getName());
        return "main";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("standardDate", new Date());
        model.addAttribute("countries", countryService.getAllCountries());
        return "signup";
    }
}