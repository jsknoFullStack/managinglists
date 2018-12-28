package com.jskno.managinglistsbe.servicies;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ValidationErrorsService {

    public Map<String, String> mapErrorsToMap(BindingResult result) {
        Map<String, String> annotationsErrors = result.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        if(MapUtils.isEmpty(annotationsErrors)) {
            annotationsErrors = result.getAllErrors().stream().collect(Collectors.toMap(
                    x -> x.getDefaultMessage().split(Pattern.quote("->"))[0],
                    x -> x.getDefaultMessage().split(Pattern.quote("->"))[1])
            );
        }
        return annotationsErrors;
    }

    public ResponseEntity<?> mapErrorsToResponseEntity(BindingResult result) {

        if(result.hasErrors()) {
            return new ResponseEntity<>(
                    result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)),
                    HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    public Map<String, String> mapErrorsToMap(Set<ConstraintViolation<?>> violations) {
        return violations.stream().collect(Collectors.toMap(
                x -> x.getMessage().split(Pattern.quote("->"))[0],
                x -> x.getMessage().split(Pattern.quote("->"))[1])
        );
    }

    public Map<String, String> mapErrorsToMap(MethodArgumentTypeMismatchException ex) {
        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();
        return Collections.singletonMap(ex.getName(),
                StringUtils.join("Expected type: ", ex.getRequiredType().getName(), ". Received value with wrong type: ", ex.getValue()));
    }
}
