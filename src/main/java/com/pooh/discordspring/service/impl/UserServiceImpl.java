package com.pooh.discordspring.service.impl;

import com.pooh.discordspring.dto.SuccessOrFailDto;
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
    public SuccessOrFailDto sendFriendRequest(String token, String friendUsername) {
        String username = "";
        if(StringUtils.hasText(token)&&tokenProvider.validateToken(token)){
            username = tokenProvider.getUsername(token);
        }
        User user = userRepository.findByUsername(username).orElseThrow();
        User friend = userRepository.findByUsername(friendUsername).orElseThrow();
        if(friend.getFriendRequests().contains(user)){
           new SuccessOrFailDto(false);
        }
        friend.getFriendRequest(user);
        userRepository.save(friend);

        return new SuccessOrFailDto(true);
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
    public void removeFriendRequest(String token, String friendUsername) {
        String username = tokenProvider.getUsername(token);
        User user = this.userRepository.findByUsername(username).orElseThrow();
        User friend = this.userRepository.findByUsername(friendUsername).orElseThrow();
        user.removeFriendRequest(friend);
        this.userRepository.save(user);
    }

    @Override
    public User getUserFromHeaders(Map<String, String> headers) {
        String token;
        token = headers.get("authorization");
        token = token.split(" ")[1];
        String username = tokenProvider.getUsername(token);
        return userRepository.findByUsername(username).orElseThrow();

    }
    @Override
    public List<UserDto> getFriendRequests(String token){
        String username = tokenProvider.getUsername(token);
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getFriendRequests().stream().map(User::userToDto).collect(Collectors.toList());
    }
}
