package com.pooh.discordspring.controller;

import com.pooh.discordspring.dto.StringMessageDto;
import com.pooh.discordspring.dto.UserDto;
import com.pooh.discordspring.entity.User;
import com.pooh.discordspring.exceptions.ErrorAPIException;
import com.pooh.discordspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @GetMapping("/sentFriendRequests")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UserDto>> getSentFriendRequests(@RequestHeader Map<String,String> headers){
        User user = userService.getUserFromHeaders(headers);
        return new ResponseEntity<>(userService.getSentFriendRequests(user.getId()),HttpStatus.OK);
    }
    @PostMapping("/name")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UserDto>> getPossibleUsers(@RequestHeader Map<String, String> headers,
            @RequestBody String friendUsername) {
        return new ResponseEntity<>(userService.getRelatedUsernames(friendUsername), HttpStatus.OK);
    }

    @PostMapping("/friends")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<StringMessageDto> addFriend(@RequestHeader Map<String, String> headers,
            @RequestBody String friendUsername) {
        String token;
        try {
            token = headers.get("authorization");
            token = token.split(" ")[1];
            userService.addFriend(token, friendUsername);
            return new ResponseEntity<>(new StringMessageDto("success"), HttpStatus.OK);
        } catch (Exception e) {
            throw new ErrorAPIException(HttpStatus.BAD_REQUEST, "invalid User");
        }
    }

    @PostMapping("/friends/request")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> sendFriendRequest(@RequestHeader Map<String, String> headers,
            @RequestBody String friendUsername) {
        String token;
        try {
            token = headers.get("authorization");
            token = token.split(" ")[1];
            String response = userService.sendFriendRequest(token, friendUsername);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ErrorAPIException(HttpStatus.BAD_REQUEST, "invalid User");
        }
    }

    // @GetMapping("/friend")
    // @PreAuthorize("hasRole('USER')")
    // public ResponseEntity<UserDto> getFriend(@RequestHeader Map<String, String>
    // headers,@RequestBody String username) {
    //
    // return new ResponseEntity<>(userService.getFriend(username),HttpStatus.OK);
    // }
    @GetMapping("/friends")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UserDto>> getFriends(@RequestHeader Map<String, String> headers) {
        String token;
        try {
            token = headers.get("authorization");
            token = token.split(" ")[1];
            List<UserDto> response = userService.getFriends(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ErrorAPIException(HttpStatus.BAD_REQUEST, "invalid User");
        }
    }
}
