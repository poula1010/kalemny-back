package com.pooh.discordspring.service;

import com.pooh.discordspring.dto.JwtAuthResponse;
import com.pooh.discordspring.dto.LoginDto;
import com.pooh.discordspring.dto.RegisterDto;

public interface AuthService{
    String register(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto LoginDto);
}
