package com.devtrack.tickets.exception;

import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
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

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UnsupportedTicketTypeException.class)
    public ResponseEntity<Map<String, String>> handleUnsupportedTicketType(UnsupportedTicketTypeException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleUnreadableBody(HttpMessageNotReadableException exception) {
        Throwable rootCause = findRootCause(exception);

        if (rootCause instanceof UnsupportedTicketTypeException unsupportedTicketTypeException) {
            return handleUnsupportedTicketType(unsupportedTicketTypeException);
        }

        if (rootCause instanceof InvalidTypeIdException invalidTypeIdException) {
            UnsupportedTicketTypeException mappedException =
                    new UnsupportedTicketTypeException(invalidTypeIdException.getTypeId());
            return handleUnsupportedTicketType(mappedException);
        }

        return ResponseEntity.badRequest().body(Map.of("error", "Malformed request body"));
    }

    private Throwable findRootCause(Throwable throwable) {
        Throwable current = throwable;
        while (current.getCause() != null) {
            current = current.getCause();
        }
        return current;
    }
}
