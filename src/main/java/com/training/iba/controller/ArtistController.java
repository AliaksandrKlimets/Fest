package com.training.iba.controller;

import com.training.iba.entity.Artist;
import com.training.iba.entity.Festival;
import com.training.iba.entity.Genres;
import com.training.iba.repository.ArtistRepository;
import com.training.iba.repository.FestivalRepository;
import com.training.iba.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/artists")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private ArtistService artistService;

    @GetMapping
    public Iterable<Artist> artists() {
        return artistRepository.findAll();
    }

    @GetMapping("/available/{date}")
    public List<Artist> artistsAvailable(@PathVariable("date") Date date) {
        return artistService.getArtistsAvailable(date);
    }

    @GetMapping("/genres/{genre}")
    public List<Artist> getArtistsByGenre(@PathVariable("genre") String genre) {
        return artistRepository.findByGenres(Genres.valueOf(genre));
    }

    @GetMapping("/{id}")
    public Artist getArtist(@PathVariable long id) {
        return artistRepository.findById(id);
    }

    @PostMapping("/add")
    public void addArtist(@RequestBody Artist artist) {
        artistRepository.save(artist);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteArtist(@PathVariable("id") Artist artist) {
        artistRepository.delete(artist);
    }

    @PutMapping("/update/artist/{id}")
    public void changeArtist(@RequestBody Artist artist, @PathVariable("id") long id) {
        Artist art = artistRepository.findById(id);
        artist.setId(art.getId());
        artistRepository.save(artist);
    }

    @GetMapping("/{id}/festivals")
    public List<Festival> getArtistsFestivals(@PathVariable("id") Artist artist) {
        List<Festival> festivals = new ArrayList<>();
        for (Festival festival : festivalRepository.findAll()) {
            if (festival.getArtists().contains(artist)) festivals.add(festival);
        }
        return festivals.stream().sorted((Festival a, Festival b) ->
                Long.valueOf(a.getFestDate().getTime()).compareTo(Long.valueOf(b.getFestDate().getTime()))).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<Artist> searchArtist(@RequestParam String name) {
        List<Artist> artists = new ArrayList<>();
        for (Artist artist : artistRepository.findAll()) {
            artists.add(artist);
        }
        return artists.stream().filter(item -> item.getArtistName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }
}
