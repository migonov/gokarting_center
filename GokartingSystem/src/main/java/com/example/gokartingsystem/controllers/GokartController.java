package com.example.gokartingsystem.controllers;

import com.example.gokartingsystem.exceptions.GokartNotFoundException;
import com.example.gokartingsystem.exceptions.ReservationException;
import com.example.gokartingsystem.services.GokartService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/gokarts")
public class GokartController {
    private final GokartService gokartService;

    public GokartController(GokartService gokartService) {
        this.gokartService = gokartService;
    }

    @PostMapping("/{gokartId}/reserve/{slotNumber}")
    public String reserveGokart(@PathVariable long gokartId, @PathVariable int slotNumber, Principal principal)
            throws ReservationException, GokartNotFoundException {
        return gokartService.reserveGokart(gokartId, slotNumber, principal);
    }
}
