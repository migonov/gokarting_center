package com.example.gokartingsystem.repositories;

import com.example.gokartingsystem.entities.Ticket;
import com.example.gokartingsystem.entities.Track;
import com.example.gokartingsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByReservedBy(User user);
    List<Ticket> findByTrack(Track track);
}
