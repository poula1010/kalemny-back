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

    @ManyToMany()
    @JoinTable(
            name = "friend_requests",
            joinColumns = @JoinColumn(name="friend_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private Set<User> friendRequests;


    @ManyToMany()
    @JoinTable(
            name="friendships",
            joinColumns = @JoinColumn(name="friend_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private Set<User> friends;


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    private Set<Role> roles;

//    public List<String> getFriendsNames(){
//        List<String> friendNames = new ArrayList<>();
//        for(Friend friend:friends){
//            friendNames.add(friend.getUser().getName());
//        }
//        return friendNames;
//    }
    public void addFriend(User user){
        if(friends == null){
            friends = new HashSet<>();
        }
        if(friends.contains(user)) {
            return;
        }
        friends.add(user);
    }
    public void getFriendRequest(User friend){
        if(friendRequests == null){
            friendRequests = new HashSet<>();
        }
        friendRequests.add(friend);
    }

    public void removeFriendRequest(User friend){
        if(friendRequests.contains(friend)){
            this.friendRequests.remove(friend);
        }
    }
    public static UserDto userToDto(User user){
        return new UserDto(user.getUsername(),user.getId(), user.getEmail(), user.getImage());
    }
}
