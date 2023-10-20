package com.carefirst.employeemanagement.api.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    private static final String ERROR = "Error {}";

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ApiException> handleNoSuchElementException(NoSuchElementException ex) {
        log.debug(ERROR, ex.getLocalizedMessage(), ex);
        ApiException apiError = new ApiException();
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setType(ApiException.ErrorType.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiException> handle(NotFoundException ex) {
        log.debug(ERROR, ex.getLocalizedMessage(), ex);
        ApiException apiError = new ApiException();
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setType(ApiException.ErrorType.NOT_FOUND);
        apiError.setError("Not found");
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({JsonMappingException.class})
    public ResponseEntity<ApiException> handleJsonMapper(JsonMappingException ex) {
        log.debug(ERROR, ex.getLocalizedMessage(), ex);
        ApiException apiError = new ApiException();
        apiError.setStatus(HttpStatus.EXPECTATION_FAILED);
        apiError.setType(ApiException.ErrorType.VALIDATION);
        apiError.setError("Json Mapping failed");
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({JsonProcessingException.class})
    public ResponseEntity<ApiException> handleJsonMapper(JsonProcessingException ex) {
        log.debug(ERROR, ex.getLocalizedMessage(), ex);
        ApiException apiError = new ApiException();
        apiError.setStatus(HttpStatus.EXPECTATION_FAILED);
        apiError.setType(ApiException.ErrorType.VALIDATION);
        apiError.setError("Json processing failed");
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ApiException> handleDataIntegritytError(DataIntegrityViolationException ex) {
        log.debug("Data integrity exception- {}", ex.getLocalizedMessage(), ex);
        ApiException apiError = new ApiException();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setType(ApiException.ErrorType.VALIDATION);
        apiError.setError("The ID has already been used.");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiException> handleBadRequest(IllegalArgumentException ex) {
        log.debug(ERROR, ex.getLocalizedMessage(), ex);
        ApiException apiError = new ApiException();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setType(ApiException.ErrorType.UNKNOWN);
        apiError.setError("Invalid input");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ApiException> handleAll(Throwable ex) {
        log.error("Internal error - {}", ex.getLocalizedMessage(), ex);
        ApiException apiError = new ApiException();
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setType(ApiException.ErrorType.UNKNOWN);
        apiError.setError("Unexpected error");
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
