package com.example.gokartingsystem.dto;

import com.example.gokartingsystem.entities.Ticket;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class KafkaMessage implements Serializable {
    private LocalDateTime date;
    private String gokart;
    private int horsepower;
    private String name;
    private BigDecimal time;

    public KafkaMessage(Ticket ticket) {
        this.date = ticket.getReservationDate();
        this.gokart = ticket.getGokart().getName();
        this.horsepower = ticket.getGokart().getHorsepower();
        this.name = ticket.getReservedBy().getUsername();
        // hardcoded
        this.time = BigDecimal.valueOf(33.12);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getGokart() {
        return gokart;
    }

    public void setGokart(String gokart) {
        this.gokart = gokart;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTime() {
        return time;
    }

    public void setTime(BigDecimal time) {
        this.time = time;
    }
}
