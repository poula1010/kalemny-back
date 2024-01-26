package com.pooh.discordspring.service;

import com.pooh.discordspring.dto.SuccessOrFailDto;
import com.pooh.discordspring.dto.UserDto;
import com.pooh.discordspring.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

     void addFriend(String token,String friendUsername);
//    public UserDto getFriend(String username);
     List<UserDto> getFriends(String token);
     SuccessOrFailDto sendFriendRequest(String token, String friendUsername);

     List<UserDto> getFriendRequests(String token);
     List<UserDto> getRelatedUsernames(String username);

     List<UserDto> getSentFriendRequests(long id);
     void removeFriendRequest(String token,String friendUsername);
    User getUserFromHeaders(Map<String,String> headers);

    void addMessageToRoom(String token, long roomId,String message);
//
//    public UserDto removeFriend(String token,Long friendId);
}
