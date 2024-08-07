package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NegocioException extends RuntimeException {


    public NegocioException(String mensagem) {
        super(mensagem);
    }

    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}
