package com.frank.api.controller;

import com.frank.api.service.setup.CityService;
import com.frank.api.service.setup.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;

@Controller
public class MainController {

    @Autowired
    private CountryService countryService;


    @Autowired
    private CityService cityService;

    @RequestMapping("/")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public String main(Principal principal,Model model) {
        model.addAttribute("username",principal.getName());
        return "main";
    }


    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("countries", countryService.getAllCountries());
        return "signup";
    }

    @GetMapping("/country/cities/{id}")
    public String cities(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("cities", cityService.findAllByCountryId(id));
        return "signup";
    }
}