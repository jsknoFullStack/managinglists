package com.jskno.managinglistsbe.security.controller.advice;

import com.jskno.managinglistsbe.security.controller.UserController;
import com.jskno.managinglistsbe.security.exceptions.UsernameAlreadyExistsException;
import com.jskno.managinglistsbe.security.exceptions.UsernameAlreadyExistsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(assignableTypes = {UserController.class})
@RestController
public class SecurityExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        UsernameAlreadyExistsResponse exceptionResponse = new UsernameAlreadyExistsResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
