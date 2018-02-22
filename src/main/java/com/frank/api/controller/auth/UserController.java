package com.frank.api.controller.auth;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.helper.RandomString;
import com.frank.api.model.auth.Role;
import com.frank.api.model.auth.User;
import com.frank.api.model.setup.Branch;
import com.frank.api.model.setup.Shop;
import com.frank.api.service.auth.RoleService;
import com.frank.api.service.auth.UserService;
import com.frank.api.service.setup.BranchService;
import com.frank.api.service.setup.CityService;
import com.frank.api.service.setup.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/api")
public class UserController extends RestBaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private CityService cityService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Get All Users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //Get all Users - paginated
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/paginated")
    public Page<User> getPaginatedUsers(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return userService.getPaginatedUsers(page, perPage);
    }

    // Create a new User
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    // Get a Single User
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    // Update a User
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setBranch(userDetails.getBranch());
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete a User
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteUser(user);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @RequestMapping(value = "/users/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> registerShop(@Valid @RequestBody User user){

        RandomString randomString = new RandomString();
        String shopCode = randomString.randomString(8);
        String branchCode = randomString.randomString(12);
        Role userRole = roleService.getRoleByName("ADMIN");

        if(userService.getUserByUsername(user.getUsername()) == null){
            HashMap<String, Object> res = new HashMap<>();
            res.put("status", 0);
            res.put("errorMessage", "USERNAME_EXISTS");
            return res;
        }

        if(userService.getByEmail(user.getEmail()) == null){
            HashMap<String, Object> res = new HashMap<>();
            res.put("status", 0);
            res.put("errorMessage", "EMAIL_EXISTS");
            return res;
        }

        if(userService.getByMobile(user.getMobile()) == null){
            HashMap<String, Object> res = new HashMap<>();
            res.put("status", 0);
            res.put("errorMessage", "MOBILE_NUMBER_EXISTS");
            return res;
        }

        //create shop
        Shop shop = new Shop();
        shop.setCode(shopCode);
        shop.setEmail(user.getEmail());
        shop.setMobile(user.getMobile());
        shop.setName("Shop-"+shopCode);
        Shop shopCreated = shopService.createShop(shop);

        //create branch
        Branch branch = new Branch();
        branch.setName("Branch-"+branchCode);
        branch.setCode(branchCode);
        branch.setEmail(user.getEmail());
        branch.setHeadquarter(Boolean.TRUE);
        branch.setShop(shopCreated);
        branch.setCity(cityService.getCityByCode("DAR"));
        Branch branchCreated = branchService.createBranch(branch);

        //create admin user
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setMobile(user.getMobile());
        user.setEmail(user.getEmail());
        user.setBranch(branchCreated);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userService.createUser(user);


        HashMap<String, Object> res = new HashMap<>();
        res.put("status", 1);
        res.put("successMessage", "CREATE_SUCCESS");
        return res;
    }
}