package com.example.gokartingsystem.exceptions;

public class UsernameAlreadyInUseException extends GokartingSystemException {
    public UsernameAlreadyInUseException() {
        super(ExceptionBody.USERNAME_ALREADY_IN_USE);
    }
}
