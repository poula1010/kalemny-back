package com.pooh.discordspring.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ErrorAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;
}
