package com.example.gokartingsystem.services;

import com.example.gokartingsystem.entities.Gokart;
import com.example.gokartingsystem.entities.Ticket;
import com.example.gokartingsystem.entities.Track;
import com.example.gokartingsystem.entities.User;
import com.example.gokartingsystem.exceptions.GokartNotFoundException;
import com.example.gokartingsystem.exceptions.ReservationException;
import com.example.gokartingsystem.repositories.GokartRepository;
import com.example.gokartingsystem.repositories.TicketRepository;
import com.example.gokartingsystem.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GokartService {
    private final GokartRepository gokartRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final Clock clock;

    public GokartService(GokartRepository gokartRepository, UserRepository userRepository, TicketRepository ticketRepository, Clock clock) {
        this.gokartRepository = gokartRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.clock = clock;
    }

    public String reserveGokart(long gokartId, int slotNumber, Principal principal)
            throws ReservationException, GokartNotFoundException {
        Gokart gokart = gokartRepository.findById(gokartId).orElseThrow(GokartNotFoundException::new);
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        Track track = gokart.getTrack();
        LocalDateTime reservationDate = generateReservationDate(slotNumber, track);
        Ticket ticket = new Ticket(user, reservationDate, gokart, track);
        ticketRepository.save(ticket);
        return "Reservation was successful";
    }
    private LocalDateTime generateReservationDate(int slotNumber, Track track) throws ReservationException {
        if (slotNumber < 0 || slotNumber > 7)
            throw new ReservationException();

        List<Ticket> existingTickets = ticketRepository.findByTrack(track);
        LocalDateTime reservationDate = convertSlotToLocalDateTime(slotNumber);

        long ticketCount = existingTickets.stream().filter(ticket -> ticket.getReservationDate().isEqual(reservationDate)).count();

        if (ticketCount >= track.getGokartCounter())
            throw new ReservationException();

        return reservationDate;
    }

    private LocalDateTime convertSlotToLocalDateTime(int slotNumber) throws ReservationException {
        int hour = LocalDateTime.now(clock).getHour();
        // tracks open at 15
        int reservationHour = 15 + slotNumber;

        if (hour >= reservationHour)
            throw new ReservationException();

        return LocalDateTime.of(LocalDateTime.now(clock).getYear(),
                LocalDateTime.now(clock).getMonth(),
                LocalDateTime.now(clock).getDayOfMonth(),
                reservationHour, 0);
    }

}
