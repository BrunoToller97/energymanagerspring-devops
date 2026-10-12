package br.com.fiap.energymanagerspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SetorNaoEncontradoException extends RuntimeException {

    public SetorNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}