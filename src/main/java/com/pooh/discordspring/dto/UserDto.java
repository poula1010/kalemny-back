package com.pooh.discordspring.dto;

import com.pooh.discordspring.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private String username;
    private Long id;
    private String email;
//    private List<UserDto> friends;
    private String image;
}
