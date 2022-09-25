package com.villvay.blog.exceptions;

import com.villvay.blog.dto.response.ErrorResponse;
import com.villvay.blog.dto.response.RequestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> ResponseEntity<RequestResponse<T>> handleValidationErrors(MethodArgumentNotValidException exception, WebRequest request) {
        List<ErrorResponse> errorResponses = exception.getBindingResult().getFieldErrors().stream().map(exception1 -> ErrorResponse.builder()
                .resource(exception.getObjectName()).field(exception1.getField())
                .error(exception1.getDefaultMessage()).build()).toList();
        return new ResponseEntity<RequestResponse<T>>(new RequestResponse<T>(false, null, errorResponses), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public <T> ResponseEntity<RequestResponse<T>> handleResourceNotFoundException(ResourceNotFoundException exception){
        ErrorResponse response = ErrorResponse.builder().resource(exception.getResource()).error(exception.getLocalizedMessage()).build();
        return new ResponseEntity<RequestResponse<T>>(new RequestResponse<T>(false, null, List.of(response)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthException.class)
    public <T> ResponseEntity<RequestResponse<T>> handleAuthException(AuthException exception){
        ErrorResponse response = ErrorResponse.builder().error(exception.getLocalizedMessage()).build();
        return new ResponseEntity<RequestResponse<T>>(new RequestResponse<T>(false, null, List.of(response)), HttpStatus.UNAUTHORIZED);
    }
}
