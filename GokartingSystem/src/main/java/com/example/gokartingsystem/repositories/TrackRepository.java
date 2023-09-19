package com.example.gokartingsystem.repositories;

import com.example.gokartingsystem.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track, Long> {
    Optional<Track> findByName(String name);
}
