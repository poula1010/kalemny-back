package com.pooh.discordspring.service.impl;

import com.pooh.discordspring.dto.MessageDto;
import com.pooh.discordspring.entity.Messages;
import com.pooh.discordspring.entity.Room;
import com.pooh.discordspring.entity.User;
import com.pooh.discordspring.repository.RoomRepository;
import com.pooh.discordspring.repository.UserRepository;
import com.pooh.discordspring.security.JwtTokenProvider;
import com.pooh.discordspring.service.RoomService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, UserRepository userRepository, JwtTokenProvider jwtTokenProvider){
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    public void createRoom(List<Long> userIds,String roomName) {
        Room newRoom = new Room();
        List<User> users = userRepository.findAllById(userIds);
        newRoom.addUsers(users);
        newRoom.setRoomName(roomName);
        roomRepository.save(newRoom);
    }

    @Override
    public List<MessageDto> getMessages(String token, Long roomId) throws Exception {
        Room room = this.roomRepository.findById(roomId).orElseThrow();
        User user = this.userRepository.findByUsername(this.jwtTokenProvider.getUsername(token)).orElseThrow();
        if(room.getUsers().contains(user)){
            return room.getMessages().stream().map(Messages::toMessageDto).collect(Collectors.toList());
        }
        else {
            throw new Exception("not a user");
        }
    }
}
