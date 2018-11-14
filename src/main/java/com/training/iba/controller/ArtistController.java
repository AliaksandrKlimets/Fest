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

    @PostMapping("/add")
    public void addArtist(@RequestBody Artist artist){
        artistRepository.save(artist);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteArtist(@PathVariable("id") Artist artist){
        artistRepository.delete(artist);
    }

    @PutMapping("/update/artist/{id}")
    public void changeArtist(@RequestBody Artist artist, @PathVariable("id") long id){
        Artist art = artistRepository.findById(id);
        artist.setId(art.getId());
        artistRepository.save(artist);
    }
}
