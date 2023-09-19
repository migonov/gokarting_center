package com.example.gokartingsystem.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User reservedBy;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime reservationDate;

    @ManyToOne
    @JoinColumn(name = "gokart_id", nullable = false)
    private Gokart gokart;

    @ManyToOne
    @JoinColumn(name = "track_id", nullable = false)
    private Track track;

    public Ticket() {}

    public Ticket(User user, LocalDateTime date, Gokart gokart, Track track) {
        this.reservedBy = user;
        this.reservationDate = date;
        this.gokart = gokart;
        this.track = track;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(User reservedBy) {
        this.reservedBy = reservedBy;
    }

    public Gokart getGokart() {
        return gokart;
    }

    public void setGokart(Gokart gokart) {
        this.gokart = gokart;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }
}
