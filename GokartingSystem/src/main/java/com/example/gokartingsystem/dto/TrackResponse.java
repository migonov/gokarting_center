package com.example.gokartingsystem.dto;

import com.example.gokartingsystem.entities.Track;

public class TrackResponse {
    private String name;
    private int length;
    private int gokartCounter;

    public TrackResponse(Track track) {
        this.name = track.getName();
        this.length = track.getLength();
        this.gokartCounter = track.getGokartCounter();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getGokartCounter() {
        return gokartCounter;
    }

    public void setGokartCounter(int gokartCounter) {
        this.gokartCounter = gokartCounter;
    }
}
