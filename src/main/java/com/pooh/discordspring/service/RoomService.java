package com.pooh.discordspring.service;

import com.pooh.discordspring.dto.MessageDto;
import com.pooh.discordspring.entity.User;

import java.util.List;

public interface RoomService {
    void createRoom(List<Long> userIds,String roomName);
    List<MessageDto> getMessages(String token, Long roomId) throws Exception;
}
