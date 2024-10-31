package com.ej.msuser.exceptions;

public class PasswordDivergentsException extends RuntimeException {

    public PasswordDivergentsException(String senhasNaoConferem) {
        super(senhasNaoConferem);
    }
}
