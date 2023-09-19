package com.example.gokartingsystem.dto;

import com.example.gokartingsystem.entities.Ticket;

import java.time.LocalDateTime;

public class TicketResponse {
    private LocalDateTime reservationDate;
    private GokartResponse gokart;
    private TrackResponse track;

    public TicketResponse(Ticket ticket) {
        this.reservationDate = ticket.getReservationDate();
        this.gokart = new GokartResponse(ticket.getGokart());
        this.track = new TrackResponse(ticket.getTrack());
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public GokartResponse getGokart() {
        return gokart;
    }

    public void setGokart(GokartResponse gokart) {
        this.gokart = gokart;
    }

    public TrackResponse getTrack() {
        return track;
    }

    public void setTrack(TrackResponse track) {
        this.track = track;
    }
}
