package com.frank.api.service;

import com.frank.api.model.User;
import com.frank.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.findOne(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getPaginatedUsers(Integer page, Integer perPage){
        return userRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
