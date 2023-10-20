package com.carefirst.employeemanagement.api.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;


@Data
public class ApiException implements Serializable {
    @Serial
    private static final long serialVersionUID = 5547457118577308532L;

    public enum ErrorType {
        VALIDATION, SECURITY, NOT_FOUND, UNKNOWN;
    }

    private HttpStatus status;
    private String error;
    private String message;
    private ErrorType type = ErrorType.UNKNOWN;

}