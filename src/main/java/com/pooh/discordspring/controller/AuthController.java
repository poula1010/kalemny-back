package com.pooh.discordspring.controller;

import com.pooh.discordspring.dto.*;
import com.pooh.discordspring.service.AuthService;
import lombok.Getter;
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
    public ResponseEntity<SuccessOrFailDto> register(@RequestBody RegisterDto registerDto){
        SuccessOrFailDto response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> register(@RequestBody LoginDto loginDto){
//        String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse= authService.login(loginDto);

        return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/username")
    public ResponseEntity<Boolean> usernameAvailable(@RequestParam String username){
        boolean available = authService.usernameAvailable(username);
        return new ResponseEntity<Boolean>(available,HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/email")
    public ResponseEntity<Boolean> emailAvailable(@RequestParam String email){
        boolean available = authService.emailAvailable(email);
        return new ResponseEntity<Boolean>(available,HttpStatus.OK);
    }
}
