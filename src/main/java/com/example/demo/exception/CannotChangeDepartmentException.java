package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CannotChangeDepartmentException extends RuntimeException {

    public CannotChangeDepartmentException() {
        super("Nie można zmienić działu pracownika, jeśli pełni on funkcję kierownika działu.");
    }
}
