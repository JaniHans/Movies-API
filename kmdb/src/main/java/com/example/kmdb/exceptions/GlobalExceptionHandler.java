package com.example.kmdb.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse requestHandlingNoHandlerFound(ResourceNotFoundException ex) {
        return new ErrorResponse(ex.getMessage(), 404) ;

    }
    @ExceptionHandler(DeleteNotAllowedException.class)
    @ResponseStatus(value= HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse deleteNotAllowed(DeleteNotAllowedException ex) {
        return new ErrorResponse(ex.getMessage(), 400) ;

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Internal server error", ex);
        return new ResponseEntity<>(new ErrorResponse("Something has just exploded in the backend. " + ex.getMessage(), 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse methodArgumentNotValid(MethodArgumentNotValidException ex) {
        StringBuilder message = new StringBuilder("Validation failed: ");

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            message.append(String.format("[%s: %s] ", error.getField(), error.getDefaultMessage()));
        });

        return new ErrorResponse(message.toString().trim(), 400);
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<ErrorResponse>
    handleInvalidDateFormat(InvalidDateFormatException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
