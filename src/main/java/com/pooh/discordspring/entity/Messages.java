package com.pooh.discordspring.entity;

import com.pooh.discordspring.dto.MessageDto;
import com.pooh.discordspring.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.bridge.Message;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "messageContent",columnDefinition = "TEXT")
    private String messageContent;

    @Column(name="timeStamp")
    private Date timeStamp;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    @JoinColumn(name="user_id")
    private User user;

    static public MessageDto toMessageDto(Messages messages){
        MessageDto messageDto = new MessageDto();
        messageDto.setUserDto(User.userToDto(messages.user));
        messageDto.setMessage(messages.messageContent);
        messageDto.setTimeStamp(messages.timeStamp);
        return messageDto;
    }
}
