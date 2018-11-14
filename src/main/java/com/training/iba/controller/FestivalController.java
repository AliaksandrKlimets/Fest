package com.training.iba.controller;

import com.training.iba.entity.Artist;
import com.training.iba.entity.Festival;
import com.training.iba.entity.Genres;
import com.training.iba.entity.User;
import com.training.iba.repository.ArtistRepository;
import com.training.iba.repository.FestivalRepository;
import com.training.iba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/festivals")
public class FestivalController {

    @Autowired
    private FestivalRepository festivalRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping
    public Iterable<Festival> festivals() {
        return festivalRepository.findAll();
    }

    @GetMapping("/genres/{genre}")
    public List<Festival> festivalsByGenre(@PathVariable String genre) {
        return festivalRepository.findByGenres(Genres.valueOf(genre));
    }

    @GetMapping("/{id}")
    public Optional<Festival> getFestival(@PathVariable Long id){
        return festivalRepository.findById(id);
    }

    @PostMapping("/add")
    public void addFestival(@RequestBody Festival festival){
        festival.setAvailable(true);
        Set<Artist> set = new HashSet<>();

        for (long l : festival.getArtistsIdList()) {
            Artist artist =  artistRepository.findById(l);
            set.add(artist);
        }
        festival.setArtists(set);

        festivalRepository.save(festival);

    }

    @PostMapping("/{id}/add/anon")
    public void addAnon(@PathVariable("id") Festival festival,  @RequestBody User user){
        user.setAnon(true);
        user.getFestivals().add(festival);
        userRepository.save(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFestival(@PathVariable("id") Festival festival){
        festivalRepository.delete(festival);
    }

    @PutMapping("/update/festival/{id}")
    public void updateFestival(@RequestBody Festival festival, @PathVariable("id") long id){
        Festival fest = festivalRepository.findById(id);
        festival.setId(id);
        festival.setArtists(fest.getArtists());
        festival.setParticipants(fest.getParticipants());
        festivalRepository.save(festival);
    }

    @GetMapping("/close/available/{id}")
    public void closeAvailable(@PathVariable("id") long id){
        Festival festival = festivalRepository.findById(id);
        festival.setAvailable(false);
        festivalRepository.save(festival);
    }

    @GetMapping("/open/available/{id}")
    public void openAvailable(@PathVariable("id") long id){
        Festival festival = festivalRepository.findById(id);
        festival.setAvailable(true);
        festivalRepository.save(festival);
    }
}
