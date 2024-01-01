package com.pooh.discordspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(nullable = false,unique = true,name = "username")
    private String username;

    @Column(nullable = false,unique = true,name = "email")
    private String email;

    @Column(nullable = false,name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "user_friends", joinColumns = @JoinColumn(name = "userId") , inverseJoinColumns = @JoinColumn(name = "friendId") )
    private List<User> userFriends;

//    @ManyToMany
//    @JoinTable(name = "user_friend_requests", joinColumns = @JoinColumn(name = "userId") , inverseJoinColumns = @JoinColumn(name = "friendId") )
//    private List<User> friendRequests;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
//
//    public void addFriend(User friend){
//        if(userFriends == null){
//            userFriends=new ArrayList<>();
//        }
//        userFriends.add(friend);
//    }
    private Set<Role> roles;
}
