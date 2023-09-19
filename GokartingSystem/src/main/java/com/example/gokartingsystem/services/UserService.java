package com.example.gokartingsystem.services;

import com.example.gokartingsystem.dto.TicketResponse;
import com.example.gokartingsystem.dto.UserRequest;
import com.example.gokartingsystem.entities.User;
import com.example.gokartingsystem.exceptions.UsernameAlreadyInUseException;
import com.example.gokartingsystem.repositories.TicketRepository;
import com.example.gokartingsystem.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TicketRepository ticketRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.ticketRepository = ticketRepository;
    }

    public String register(UserRequest user) throws UsernameAlreadyInUseException {
        Optional<User> existingUser = userRepository.findByUsername(user.username());
        if (existingUser.isPresent())
            throw new UsernameAlreadyInUseException();
        User newUser = new User(user.username(), passwordEncoder.encode(user.password()));
        userRepository.save(newUser);
        return "Registration was successful";
    }

    public List<TicketResponse> getUserReservations(Principal principal) {
        Optional<User> currentUser = userRepository.findByUsername(principal.getName());
        return ticketRepository.findByReservedBy(currentUser.get()).stream().map(TicketResponse::new).collect(Collectors.toList());
    }
}
