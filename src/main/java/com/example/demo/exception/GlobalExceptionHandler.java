package com.example.demo.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    ErrorMessage exceptionHandler(ValidationException e) {
        log.error(e.getMessage());
        return new ErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    ErrorMessage exceptionHandler(InvalidParameterException e) {
        log.error(e.getMessage());
        return new ErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    ErrorMessage exceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return new ErrorMessage(HttpStatus.BAD_REQUEST, "Deserialization/Parsing error, please check your input fields");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    ErrorMessage exceptionHandler(Exception e) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST, "Unexpected exception occurred");
    }

}
