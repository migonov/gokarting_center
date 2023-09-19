package com.example.gokartingsystem.dto;

import com.example.gokartingsystem.entities.Gokart;

import java.io.Serializable;

public class GokartResponse implements Serializable {
    private String name;
    private int horsepower;
    private TrackResponse track;

    public GokartResponse(Gokart gokart) {
        this.name = gokart.getName();
        this.horsepower = gokart.getHorsepower();
        this.track = new TrackResponse(gokart.getTrack());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public TrackResponse getTrack() {
        return track;
    }

    public void setTrack(TrackResponse track) {
        this.track = track;
    }
}
