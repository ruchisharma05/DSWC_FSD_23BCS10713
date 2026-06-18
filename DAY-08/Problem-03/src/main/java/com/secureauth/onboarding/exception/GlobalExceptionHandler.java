package com.secureauth.onboarding.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new LinkedHashMap<>();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        for (ObjectError globalError : exception.getBindingResult().getGlobalErrors()) {
            errors.put(toErrorKey(globalError), globalError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }

    private String toErrorKey(ObjectError globalError) {
        String code = globalError.getCode();
        if (code == null || code.isBlank()) {
            return "globalError";
        }

        return Character.toLowerCase(code.charAt(0)) + code.substring(1);
    }
}
