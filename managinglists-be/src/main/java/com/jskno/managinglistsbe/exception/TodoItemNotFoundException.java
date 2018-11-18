package com.jskno.managinglistsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TodoItemNotFoundException extends RuntimeException {

    public TodoItemNotFoundException(String message) {
        super(message);
    }

}
