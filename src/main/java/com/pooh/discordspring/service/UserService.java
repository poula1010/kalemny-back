package com.pooh.discordspring.service;

import com.pooh.discordspring.dto.UserDto;

import java.util.List;

public interface UserService {

    public UserDto addFriend(String token,String friendUsername);
    public UserDto getFriend(String username);
    public List<UserDto> getFriends(String token);
    public String sendFriendRequest(String token, String friendUsername);

    public List<String> getRelatedUsernames(String username);
//
//    public List<UserDto> getFriends(String token);
//
//    public UserDto removeFriend(String token,Long friendId);
}
