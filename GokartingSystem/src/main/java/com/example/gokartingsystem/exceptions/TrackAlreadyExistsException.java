package com.example.gokartingsystem.exceptions;

public class TrackAlreadyExistsException extends GokartingSystemException {
    public TrackAlreadyExistsException() {
        super(ExceptionBody.TRACK_ALREADY_EXISTS);
    }
}
