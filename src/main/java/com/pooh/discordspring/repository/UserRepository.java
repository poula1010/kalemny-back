package com.pooh.discordspring.repository;

import com.pooh.discordspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameOrEmail(String username,String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username,String email);

    @Query(value = "SELECT username FROM users WHERE username LIKE %:name% ",
            nativeQuery = true)
    List<String> findRelatedUsernames(@Param("name") String username);
}
