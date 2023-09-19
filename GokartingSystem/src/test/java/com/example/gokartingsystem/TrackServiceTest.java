package com.example.gokartingsystem;

import com.example.gokartingsystem.dto.TrackRequest;
import com.example.gokartingsystem.dto.TrackResponse;
import com.example.gokartingsystem.entities.Track;
import com.example.gokartingsystem.exceptions.TrackAlreadyExistsException;
import com.example.gokartingsystem.repositories.TrackRepository;
import com.example.gokartingsystem.services.TrackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TrackServiceTest {
    @Mock
    private TrackRepository trackRepository;
    @Autowired
    @InjectMocks
    private TrackService trackService;

    @Test
    public void givenPreviouslyExistentTrackRequest_whenAddTrack_thenThrowTrackAlreadyExistsException() {
        String trackName = "Monza";
        TrackRequest trackRequest = new TrackRequest(trackName, 6200);

        Mockito.when(trackRepository.findByName(trackName)).thenReturn(Optional.of(new Track()));

        assertThrows(TrackAlreadyExistsException.class, () -> {
            trackService.addTrack(trackRequest);
        });
    }

    @Test
    public void givenTrackRequest_whenAddTrack_thenReturnTrackResponse() {
        String trackName = "Monza";
        int trackLength = 6200;
        TrackRequest trackRequest = new TrackRequest(trackName, trackLength);
        Track newTrack = new Track(trackName, trackLength);

        Mockito.when(trackRepository.findByName(trackName)).thenReturn(Optional.empty());
        Mockito.when(trackRepository.save(Mockito.any())).thenReturn(newTrack);

        TrackResponse trackResponse = trackService.addTrack(trackRequest);
        assertEquals(trackResponse.getName(), trackName);
        assertEquals(trackResponse.getLength(), trackLength);
        assertEquals(trackResponse.getGokartCounter(), 0);
    }

}
