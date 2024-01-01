package com.pooh.discordspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_friends")
public class UserFriends {
    @Id
    @Column
    private int friendId;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User user;


}
