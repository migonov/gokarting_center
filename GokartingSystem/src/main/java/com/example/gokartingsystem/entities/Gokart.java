package com.example.gokartingsystem.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "gokarts")
public class Gokart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int horsepower;

    @ManyToOne
    @JoinColumn(name = "track_id", nullable = false)
    private Track track;

    @OneToMany(mappedBy = "gokart", cascade = CascadeType.ALL)
    private List<Ticket> ticket;

    public Gokart() {}

    public Gokart(String name, int horsepower, Track track) {
        this.name = name;
        this.horsepower = horsepower;
        this.track = track;
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

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
