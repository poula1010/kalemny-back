package com.pooh.discordspring.repository;

import com.pooh.discordspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameOrEmail(String username,String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username,String email);
}
