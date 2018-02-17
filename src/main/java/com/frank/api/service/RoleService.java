package com.frank.api.service;

import com.frank.api.model.Role;
import com.frank.api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role getRoleById(Long id){
        return roleRepository.findOne(id);
    }

    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Page<Role> getPaginatedRoles(Integer page, Integer perPage){
        return roleRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteRole(Role role){
        roleRepository.delete(role);
    }
}
