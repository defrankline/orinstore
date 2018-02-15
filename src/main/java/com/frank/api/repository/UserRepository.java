package com.frank.api.repository;

import com.frank.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
    public User findByEmail(String email);
    public User findByUsernameAndPassword(String username,String Password);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM users c WHERE c.username = :username")
    boolean existsByUsername(@Param("username") String username);
}