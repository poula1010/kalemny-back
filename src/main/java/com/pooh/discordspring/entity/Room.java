package com.pooh.discordspring.entity;

import com.pooh.discordspring.dto.RoomDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name= "rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "room_users",
            joinColumns = @JoinColumn(name="room_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private Set<User> users;
    public void addUsers(List<User> users){
        if(this.users == null){
            this.users = new HashSet<>();
        }
        this.users.addAll(users);
    }

    @Column(name = "room_name")
    private String roomName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Messages> messages;


    public void addMessage(Messages message){
        if(this.messages == null){
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
    }

    public static RoomDto toRoomDto(Room room){
        List<Long> userIds ;
        userIds = room.getUsers().stream().map(User::getId).collect(Collectors.toList());
        RoomDto roomDto = new RoomDto(userIds, room.getId(), room.getRoomName());
        return roomDto;
    }
}
