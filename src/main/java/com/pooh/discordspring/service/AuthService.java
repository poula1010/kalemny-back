package com.pooh.discordspring.service;

import com.pooh.discordspring.dto.JwtAuthResponse;
import com.pooh.discordspring.dto.LoginDto;
import com.pooh.discordspring.dto.RegisterDto;
import com.pooh.discordspring.entity.User;

import java.util.Map;

public interface AuthService{
    String register(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto LoginDto);


}
