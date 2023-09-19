package com.example.gokartingsystem.controllers;

import com.example.gokartingsystem.dto.TicketResponse;
import com.example.gokartingsystem.dto.UserRequest;
import com.example.gokartingsystem.exceptions.UsernameAlreadyInUseException;
import com.example.gokartingsystem.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String userRegister(@RequestBody UserRequest userRequest) throws UsernameAlreadyInUseException {
        return userService.register(userRequest);
    }

    @GetMapping("/reservations")
    public List<TicketResponse> getReservations(Principal principal) {
        return userService.getUserReservations(principal);
    }
}
