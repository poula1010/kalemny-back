package com.pooh.discordspring.service.impl;

import com.pooh.discordspring.dto.UserDto;
import com.pooh.discordspring.entity.User;
import com.pooh.discordspring.repository.UserRepository;
import com.pooh.discordspring.security.JwtTokenProvider;
import com.pooh.discordspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private JwtTokenProvider tokenProvider;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(JwtTokenProvider jwtTokenProvider,UserRepository userRepository){
        this.tokenProvider=jwtTokenProvider;
        this.userRepository= userRepository;

    }
    @Override
    public void addFriend(String token, String  friendUsername) {
        String username = "";
        if(StringUtils.hasText(token)&&tokenProvider.validateToken(token)){
            username = tokenProvider.getUsername(token);
        }
        User user = userRepository.findByUsername(username).orElseThrow();
        User friend = userRepository.findByUsername(friendUsername).orElseThrow();

        user.addFriend(friend);
        friend.addFriend(user);

        user.removeFriendRequest(friend);
        friend.removeFriendRequest(user);
        // Save the user with the updated friend list
        userRepository.save(user);
        userRepository.save(friend);
    }

//    @Override
//    public UserDto getFriend(String username) {
//        User user = userRepository.findByUsername(username).orElseThrow();
//        return User.userToDto(user);
//    }
    @Override
    public List<UserDto> getFriends(String token) {
        String username = "";
        if(StringUtils.hasText(token)&&tokenProvider.validateToken(token)){
            username = tokenProvider.getUsername(token);
        }
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getFriends().stream().map(User::userToDto).collect(Collectors.toList());
    }
    @Override
    public String sendFriendRequest(String token, String friendUsername) {
        String username = "";
        if(StringUtils.hasText(token)&&tokenProvider.validateToken(token)){
            username = tokenProvider.getUsername(token);
        }
        User user = userRepository.findByUsername(username).orElseThrow();
        User friend = userRepository.findByUsername(friendUsername).orElseThrow();
        if(friend.getFriendRequests().contains(user)){
            return "There is Already a friend Request";
        }
        friend.getFriendRequest(user);
        userRepository.save(friend);

        return "Friend Request Sent Successfully";
    }

    @Override
    public List<UserDto> getRelatedUsernames(String username) {
        return userRepository.findRelatedUsernames(username).stream().map(User::userToDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getSentFriendRequests(long id){
        return userRepository.getSentFriendRequestsById(id).stream().map(User::userToDto).collect(Collectors.toList());
    }

    @Override
    public User getUserFromHeaders(Map<String, String> headers) {
        String token;
        token = headers.get("authorization");
        token = token.split(" ")[1];
        String username = tokenProvider.getUsername(token);
        return userRepository.findByUsername(username).orElseThrow();

    }
}
