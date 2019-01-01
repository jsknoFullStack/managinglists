package com.jskno.managinglistsbe.web.advice;

import com.jskno.managinglistsbe.exception.*;
import com.jskno.managinglistsbe.servicies.ValidationErrorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @Autowired
    private ValidationErrorsService validationErrorsService;

    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<Object> handle(TopicNotFoundException ex) {
        TopicNotFoundExceptionResponse exceptionResponse = new TopicNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TodoItemNotFoundException.class)
    public ResponseEntity<Object> handle(TodoItemNotFoundException ex) {
        TodoItemNotFoundExceptionResponse exceptionResponse = new TodoItemNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(EntityConstraintViolationException ex) {
        Map<String, String> map = new HashMap<>();
        map.put(ex.getConstraintRule(), ex.getMessage());
        return map;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(ConstraintViolationException ex) {
        return validationErrorsService.mapErrorsToMap(ex.getConstraintViolations());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(MethodArgumentNotValidException ex) {
        return validationErrorsService.mapErrorsToMap(ex.getBindingResult());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(MethodArgumentTypeMismatchException ex) {
        return validationErrorsService.mapErrorsToMap(ex);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(DataIntegrityViolationException ex) {
        Map<String, String> map = new HashMap<>();
        map.put(ex.getLocalizedMessage(), ex.getMessage());
        return map;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EntityNotFoundResponse handle(EntityNotFoundException ex) {
        return new EntityNotFoundResponse(
                ex.getMessage().split(Pattern.quote("->"))[0],
                ex.getMessage().split(Pattern.quote("->"))[1]);
    }
}
