package com.example.gokartingsystem.exceptions;

public class GokartNotFoundException extends GokartingSystemException {
    public GokartNotFoundException() {
        super(ExceptionBody.GOKART_NOT_FOUND);
    }
}
