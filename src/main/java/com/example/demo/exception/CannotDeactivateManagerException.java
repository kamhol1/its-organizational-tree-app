package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CannotDeactivateManagerException extends RuntimeException {

    public CannotDeactivateManagerException() {
        super("Nie można usunąć pracownika, który pełni funkcję kierownika działu.");
    }
}
