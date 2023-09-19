package com.example.gokartingsystem;

import com.example.gokartingsystem.repositories.GokartRepository;
import com.example.gokartingsystem.repositories.TicketRepository;
import com.example.gokartingsystem.repositories.UserRepository;
import com.example.gokartingsystem.services.GokartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class GokartServiceTest {
    @Mock
    private Principal principal;
    @Mock
    private GokartRepository gokartRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private Clock clock;
    @Autowired
    @InjectMocks
    private GokartService gokartService;

    // TODO
    @Test
    public void successfulGokartReservation() {
        assertTrue(true);
    }
}
