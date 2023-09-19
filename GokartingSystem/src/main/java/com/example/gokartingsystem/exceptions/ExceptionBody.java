package com.example.gokartingsystem.exceptions;

public enum ExceptionBody {
    GOKART_NOT_FOUND(1, "Gokart not found"),
    RESERVATION_ERROR(2, "Reservation unsuccessful"),
    TRACK_ALREADY_EXISTS(3, "Track with given name already exists"),
    TRACK_NOT_EXISTS(4, "Track does not exist"),
    USERNAME_ALREADY_IN_USE(5, "Username is already in use");

    final int errorCode;
    final String message;

    private ExceptionBody(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
