package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class EntidadeNaoEncontradaException extends NegocioException {


    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public EntidadeNaoEncontradaException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
