package com.example.gokartingsystem.exceptions;

public class TrackNotExistsException extends GokartingSystemException {
    public TrackNotExistsException() {
        super(ExceptionBody.TRACK_NOT_EXISTS);
    }
}
