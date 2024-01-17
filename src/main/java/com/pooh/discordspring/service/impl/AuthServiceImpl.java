package com.pooh.discordspring.service.impl;

import com.pooh.discordspring.dto.JwtAuthResponse;
import com.pooh.discordspring.dto.LoginDto;
import com.pooh.discordspring.dto.RegisterDto;
import com.pooh.discordspring.dto.UserDto;
import com.pooh.discordspring.entity.Role;
import com.pooh.discordspring.entity.User;
import com.pooh.discordspring.exceptions.ErrorAPIException;
import com.pooh.discordspring.repository.RoleRepository;
import com.pooh.discordspring.repository.UserRepository;
import com.pooh.discordspring.security.JwtTokenProvider;
import com.pooh.discordspring.service.AuthService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private Environment env;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;
    @Override
    public String register(RegisterDto registerDto) {
        //check if username already exists
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new ErrorAPIException(HttpStatus.BAD_REQUEST,"Username Already Exists");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new ErrorAPIException(HttpStatus.BAD_REQUEST,"Email Already Exists");
        }
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setUsername(registerDto.getUsername());
        user.setImage("atef2");
        Set<Role> roleSet = new HashSet<>();
        Role role =roleRepository.findRoleByName("ROLE_USER");
        roleSet.add(role);

        user.setRoles(roleSet);
        userRepository.save(user);

        return "User Added Successfully";
    }


    @Override
    public JwtAuthResponse login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        String username = jwtTokenProvider.getUsername(token);
        UserDto responseDto = User.userToDto(userRepository.findByUsername(username).orElseThrow());
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setUserDto(responseDto);
        jwtAuthResponse.setTokenExpiration(new Date().getTime() + Long.parseLong(Objects.requireNonNull(env.getProperty("app.jwt-expiration"))) );
        return jwtAuthResponse;
    }


}
