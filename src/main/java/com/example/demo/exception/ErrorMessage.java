package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
class ErrorMessage {
    private HttpStatus status;
    private String message;


}
