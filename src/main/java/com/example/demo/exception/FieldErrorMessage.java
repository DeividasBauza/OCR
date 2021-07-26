package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class FieldErrorMessage {
    private String field;
    private String message;

}
