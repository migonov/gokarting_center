package com.example.gokartingsystem.controllers;

import com.example.gokartingsystem.dto.GokartRequest;
import com.example.gokartingsystem.dto.GokartResponse;
import com.example.gokartingsystem.dto.TrackRequest;
import com.example.gokartingsystem.dto.TrackResponse;
import com.example.gokartingsystem.exceptions.TrackAlreadyExistsException;
import com.example.gokartingsystem.exceptions.TrackNotExistsException;
import com.example.gokartingsystem.services.TrackService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracks")
public class TrackController {
    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping()
    public Page<TrackResponse> getAllTracks(@PageableDefault(size = 5) Pageable pageable) {
        return trackService.findAllTracks(pageable);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public TrackResponse addTrack(@RequestBody TrackRequest trackRequest) throws TrackAlreadyExistsException {
        return trackService.addTrack(trackRequest);
    }

    @GetMapping("/{id}")
    public TrackResponse getTrack(@PathVariable long id) throws TrackNotExistsException {
        return trackService.getTrackById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteTrack(@PathVariable long id) throws TrackNotExistsException {
        return trackService.deleteTrackById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public TrackResponse updateTrack(@PathVariable long id, @RequestBody TrackRequest trackRequest)
            throws TrackNotExistsException{
        return trackService.updateTrack(id, trackRequest);
    }

    @GetMapping("/{id}/gokarts")
    public List<GokartResponse> getGokarts(@PathVariable long id) throws TrackNotExistsException {
        return trackService.getTrackGokarts(id);
    }

    @PostMapping("/{id}/gokarts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public GokartResponse addGokart(@PathVariable long id, @RequestBody GokartRequest gokartRequest)
            throws TrackNotExistsException{
        return trackService.addGokartToTrack(id, gokartRequest);
    }
}
