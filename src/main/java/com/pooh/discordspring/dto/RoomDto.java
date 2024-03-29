package com.pooh.discordspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private List<Long> userIds;
    private long roomId;
    private String roomName;
}
