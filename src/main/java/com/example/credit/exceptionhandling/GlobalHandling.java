package com.example.credit.exceptionhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

/**
 * GlobalHandling
 */
@ControllerAdvice
@Slf4j
public class GlobalHandling {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> illegalArgumentController(MethodArgumentTypeMismatchException ex){
        String parameterName = ex.getName(); // Name of the parameter that caused the error
        String parameterType = Objects.requireNonNull(ex.getRequiredType()).getSimpleName(); // Expected type of the parameter
        Object value = ex.getValue(); // The value that was passed

        // Log detailed error information
        log.warn("Illegal Argument passed to controller. Parameter: {}, Expected Type: {}, Provided Value: {}",
                parameterName, parameterType, value);
        String errorMessage = String.format("The value '%s' is not permitted.",
                value);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
