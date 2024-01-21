package com.pooh.discordspring.service;

import com.pooh.discordspring.dto.SuccessOrFailDto;
import com.pooh.discordspring.dto.UserDto;
import com.pooh.discordspring.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    public void addFriend(String token,String friendUsername);
//    public UserDto getFriend(String username);
    public List<UserDto> getFriends(String token);
    public SuccessOrFailDto sendFriendRequest(String token, String friendUsername);

    public List<UserDto> getFriendRequests(String token);
    public List<UserDto> getRelatedUsernames(String username);

    public List<UserDto> getSentFriendRequests(long id);
    public void removeFriendRequest(String token,String friendUsername);
    User getUserFromHeaders(Map<String,String> headers);
//
//    public UserDto removeFriend(String token,Long friendId);
}
