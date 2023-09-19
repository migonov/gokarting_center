package com.example.gokartingsystem.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tracks")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int length;

    @Column()
    private int gokartCounter;

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL)
    private List<Gokart> gokartList;

    public Track() {}

    public Track(String name, int length) {
        this.name = name;
        this.length = length;
        this.gokartCounter = 0;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getGokartCounter() {
        return gokartCounter;
    }

    public void setGokartCounter(int gokartCounter) {
        this.gokartCounter = gokartCounter;
    }
}
