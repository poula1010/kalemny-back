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
import java.util.Optional;
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
    public UserDto addFriend(String token, String  friendUsername) {
        String username = "";
        if(StringUtils.hasText(token)&&tokenProvider.validateToken(token)){
            username = tokenProvider.getUsername(token);
        }
        User user = userRepository.findByUsername(username).orElseThrow();
        User friend = userRepository.findByUsername(friendUsername).orElseThrow();
        if(user.getFriendRequests().contains(friend)){
            user.getFriendRequests().remove(friend);
            friend.getFriendRequests().remove(user);

            user.addFriend(friend);
            userRepository.save(user);
            friend.addFriend(user);
            userRepository.save(friend);
        }

        UserDto returnDto = User.userToDto(user);
        return returnDto;
    }

    @Override
    public UserDto getFriend(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return User.userToDto(user);
    }
    @Override
    public List<UserDto> getFriends(String token) {
        String username = "";
        if(StringUtils.hasText(token)&&tokenProvider.validateToken(token)){
            username = tokenProvider.getUsername(token);
        }
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getUserFriends().stream().map(User::userToDto).collect(Collectors.toList());
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
    public List<String> getRelatedUsernames(String username) {
        return userRepository.findRelatedUsernames(username);
    }
}
