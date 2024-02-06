package com.pooh.discordspring.controller;

import com.pooh.discordspring.dto.MessageDto;
import com.pooh.discordspring.dto.RoomDto;
import com.pooh.discordspring.dto.SendMessageDto;
import com.pooh.discordspring.dto.SuccessOrFailDto;
import com.pooh.discordspring.repository.RoomRepository;
import com.pooh.discordspring.service.AuthService;
import com.pooh.discordspring.service.RoomService;
import com.pooh.discordspring.service.UserService;
import com.pooh.discordspring.service.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/room")
@CrossOrigin("*")
public class RoomController {
    private RoomService roomService;
    private UserService userService;
    @Autowired
    public RoomController(RoomService roomService,UserService userService){
        this.roomService = roomService;
        this.userService = userService;
    }
    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessOrFailDto> createRoom(@RequestHeader Map<String,String> header, @RequestBody RoomDto roomDto){
        try{
            this.roomService.createRoom(roomDto.getUserIds(),roomDto.getRoomName());
            return new ResponseEntity<>(new SuccessOrFailDto(true),HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(new SuccessOrFailDto(false), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<RoomDto>> getRooms(@RequestHeader Map<String,String> header){
        try{
            String token = extractToken(header);
            List<RoomDto> rooms = this.roomService.getRooms(token);
            return new ResponseEntity<>(rooms,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("message")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessOrFailDto> sendMessage(@RequestHeader Map<String,String> header, @RequestBody SendMessageDto messageDto){
        try {
            String token = extractToken(header);
            this.userService.addMessageToRoom(token, messageDto.getRoomId(), messageDto.getMessage());
            return new ResponseEntity<>(new SuccessOrFailDto(true),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new SuccessOrFailDto(false), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("message")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<MessageDto>>  getMessages(@RequestHeader Map<String,String> header,@RequestParam Long roomId){
        try{
            String token = extractToken(header);
            List<MessageDto> response = this.roomService.getMessages(token,roomId);
            return new ResponseEntity<>(response,HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessOrFailDto> deleteRoom(@RequestHeader Map<String ,String> header,@RequestParam Long roomId){
        try{
            String token = extractToken(header);
            this.roomService.deleteRoom(token,roomId);
            return new ResponseEntity<>(new SuccessOrFailDto(true),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new SuccessOrFailDto(false),HttpStatus.BAD_REQUEST);
        }
    }
    private String extractToken(Map<String,String> headers){
        String token;
        token = headers.get("authorization");
        token = token.split(" ")[1];
        return token;
    }
}
