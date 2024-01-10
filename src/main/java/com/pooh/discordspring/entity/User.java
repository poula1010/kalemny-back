package com.pooh.discordspring.entity;

import com.pooh.discordspring.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
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

    @Column(name="image",nullable = false)
    private String image;

    @ManyToMany
    @JoinTable(name = "user_friends", joinColumns = @JoinColumn(name = "userId") , inverseJoinColumns = @JoinColumn(name = "friendId") )
    private List<User> userFriends;

    @ManyToMany
    @JoinTable(name = "friend_requests", joinColumns = @JoinColumn(name = "userId") , inverseJoinColumns = @JoinColumn(name = "friendId") )
    private Set<User> friendRequests;


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    private Set<Role> roles;
    public void addFriend(User friend){
        if(userFriends == null){
            userFriends=new ArrayList<>();
        }
        userFriends.add(friend);
    }
    public List<String> getFriendsNames(){
        List<String> friendNames = new ArrayList<>();
        for(User friend:userFriends){
            friendNames.add(friend.getName());
        }
        return friendNames;
    }

    public void getFriendRequest(User friend){
        if(friendRequests == null){
            friendRequests = new HashSet<>();
        }
        friendRequests.add(friend);
    }

    public static UserDto userToDto(User user){
        return new UserDto(user.getUsername(),user.getId(), user.getEmail(), user.getImage());
    }
}
