package com.example.gokartingsystem.repositories;

import com.example.gokartingsystem.entities.Gokart;
import com.example.gokartingsystem.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GokartRepository extends JpaRepository<Gokart, Long> {
    List<Gokart> findByTrack(Track track);
}
