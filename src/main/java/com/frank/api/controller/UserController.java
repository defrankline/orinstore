package com.frank.api.controller;

import com.frank.api.config.Config;
import com.frank.api.model.User;
import com.frank.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/api")
public class UserController extends RestBaseController{

    @Autowired
    private UserService userService;

    //Get all Users - paginated
    @GetMapping("/users/paginated")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public Page<User> getPaginatedUsers(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return userService.getPaginatedUsers(page,perPage);
    }

    @GetMapping("/users/login")
    public HashMap<String, Object> login(@RequestParam("username") String username,@RequestParam("password") String password, @RequestParam("grant_type") String grant_type) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("password", password);
        response.put("grant_type", grant_type);
        return response;
    }
}