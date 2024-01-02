package com.pooh.discordspring.controller;

import com.pooh.discordspring.dto.UserDto;
import com.pooh.discordspring.exceptions.ErrorAPIException;
import com.pooh.discordspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/friends")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDto> addFriend(@RequestHeader Map<String, String> headers,@RequestBody String friendUsername) {
        String token;
        try {
            token = headers.get("authorization");
            token = token.split(" ")[1];
            UserDto userDto = userService.addFriend(token,friendUsername);
            return new ResponseEntity<>(userDto,HttpStatus.OK);
        } catch (Exception e) {
            throw new ErrorAPIException(HttpStatus.BAD_REQUEST, "invalid User");
        }
    }
    @PostMapping("/friends/request")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> sendFriendRequest(@RequestHeader Map<String,String> headers,@RequestBody String friendUsername){
        String token;
        try {
            token = headers.get("authorization");
            token = token.split(" ")[1];
            String response = userService.sendFriendRequest(token,friendUsername);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            throw new ErrorAPIException(HttpStatus.BAD_REQUEST, "invalid User");
        }
    }
    @GetMapping("/friends")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDto> getFriend(@RequestHeader Map<String, String> headers,@RequestBody String username) {

        return new ResponseEntity<>(userService.getFriends(username),HttpStatus.OK);
    }
}
