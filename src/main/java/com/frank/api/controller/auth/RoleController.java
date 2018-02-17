package com.frank.api.controller.auth;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.Role;
import com.frank.api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/api")
public class RoleController extends RestBaseController {

    @Autowired
    private RoleService roleService;

    // Get All Roles
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    //Get all Roles - paginated
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/roles/paginated")
    public Page<Role> getPaginatedRoles(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return roleService.getPaginatedRoles(page,perPage);
    }

    // Create a new Role
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/roles")
    public Page<Role> createRole(@Valid @RequestBody Role role, @RequestParam("perPage") Integer perPage) {
        roleService.createRole(role);
        return roleService.getPaginatedRoles(0,perPage);
    }

    // Get a Single Role
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable(value = "id") Long roleId) {
        Role role = roleService.getRoleById(roleId);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(role);
    }

    // Update a Role
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/roles/{id}")
    public Page<Role> updateRole(@PathVariable(value = "id") Long roleId, @Valid @RequestBody Role roleDetails,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Role role = roleService.getRoleById(roleId);
        role.setName(roleDetails.getName());
        roleService.updateRole(role);
        return roleService.getPaginatedRoles(page,perPage);
    }

    // Delete a Role
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/roles/{id}")
    public Page<Role> deleteRole(@PathVariable(value = "id") Long roleId,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Role role = roleService.getRoleById(roleId);
        roleService.deleteRole(role);
        return roleService.getPaginatedRoles(page,perPage);
    }

}