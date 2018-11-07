package com.training.iba.controller;

import com.training.iba.entity.Artist;
import com.training.iba.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping
    public Iterable<Artist> artists() {
        return artistRepository.findAll();
    }
}
