package com.example.gokartingsystem.services;

import com.example.gokartingsystem.dto.GokartRequest;
import com.example.gokartingsystem.dto.GokartResponse;
import com.example.gokartingsystem.dto.TrackRequest;
import com.example.gokartingsystem.dto.TrackResponse;
import com.example.gokartingsystem.entities.Gokart;
import com.example.gokartingsystem.entities.Track;
import com.example.gokartingsystem.exceptions.TrackAlreadyExistsException;
import com.example.gokartingsystem.exceptions.TrackNotExistsException;
import com.example.gokartingsystem.repositories.GokartRepository;
import com.example.gokartingsystem.repositories.TrackRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackService {
    private final TrackRepository trackRepository;
    private final GokartRepository gokartRepository;

    public TrackService(TrackRepository trackRepository, GokartRepository gokartRepository) {
        this.trackRepository = trackRepository;
        this.gokartRepository = gokartRepository;
    }

    public Page<TrackResponse> findAllTracks(Pageable pageable) {
        return trackRepository.findAll(pageable).map(TrackResponse::new);
    }

    public TrackResponse addTrack(TrackRequest track) throws TrackAlreadyExistsException {
        if (trackRepository.findByName(track.name()).isPresent())
            throw new TrackAlreadyExistsException();
        Track newTrack = new Track(track.name(), track.length());
        trackRepository.save(newTrack);
        return new TrackResponse(newTrack);
    }

    public TrackResponse getTrackById(long id) throws TrackNotExistsException {
        Track track = trackRepository.findById(id).orElseThrow(TrackNotExistsException::new);
        return new TrackResponse(track);
    }

    public String deleteTrackById(long id) throws TrackNotExistsException {
        Track toDelete = trackRepository.findById(id).orElseThrow(TrackNotExistsException::new);
        trackRepository.delete(toDelete);
        return "Track has been deleted";
    }

    public TrackResponse updateTrack(long id, TrackRequest track) throws TrackNotExistsException {
        Track toUpdate = trackRepository.findById(id).orElseThrow(TrackNotExistsException::new);
        toUpdate.setLength(track.length());
        toUpdate.setName(track.name());
        trackRepository.save(toUpdate);
        return new TrackResponse(toUpdate);
    }

    public List<GokartResponse> getTrackGokarts(long id) throws TrackNotExistsException {
        Track track = trackRepository.findById(id).orElseThrow(TrackNotExistsException::new);
        return gokartRepository.findByTrack(track).stream().map(GokartResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public GokartResponse addGokartToTrack(long id, GokartRequest gokart) throws TrackNotExistsException {
        Track track = trackRepository.findById(id).orElseThrow(TrackNotExistsException::new);
        track.setGokartCounter(track.getGokartCounter() + 1);
        trackRepository.save(track);
        Gokart newGokart = new Gokart(gokart.name(), gokart.horsepower(), track);
        gokartRepository.save(newGokart);
        return new GokartResponse(newGokart);
    }
}
