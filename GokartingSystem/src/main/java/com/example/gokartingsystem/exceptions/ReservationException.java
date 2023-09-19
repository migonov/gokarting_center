package com.example.gokartingsystem.exceptions;

public class ReservationException extends GokartingSystemException {
    public ReservationException() {
        super(ExceptionBody.RESERVATION_ERROR);
    }
}
