package com.training.iba.controller;

import com.training.iba.entity.Artist;
import com.training.iba.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/artists")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping
    public Iterable<Artist> artists() {
        return artistRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Artist> getUser(@PathVariable Long id){
        return artistRepository.findById(id);
    }
}
