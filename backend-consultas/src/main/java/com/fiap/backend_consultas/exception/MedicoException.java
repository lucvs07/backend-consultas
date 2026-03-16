package com.fiap.backend_consultas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedicoException extends RuntimeException {
    public MedicoException(String message) {
        super(message);
    }
}
