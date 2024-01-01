package com.pooh.discordspring.controller;

import com.pooh.discordspring.dto.JwtAuthResponse;
import com.pooh.discordspring.dto.LoginDto;
import com.pooh.discordspring.dto.RegisterDto;
import com.pooh.discordspring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;
    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }
    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> register(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse= new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
    }
}
