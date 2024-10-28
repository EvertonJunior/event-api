package com.ej.msuser.exceptions;

public class UsernameUniqueViolationException extends RuntimeException {

    public UsernameUniqueViolationException(String msg){
        super(msg);
    }
}
