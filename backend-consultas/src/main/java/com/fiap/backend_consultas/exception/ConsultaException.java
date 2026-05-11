package com.fiap.backend_consultas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ConsultaException extends RuntimeException {
    public ConsultaException(String message) {
        super(message);
    }
}
