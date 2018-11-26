package com.training.iba.service;

import com.training.iba.entity.Artist;
import com.training.iba.entity.Festival;
import com.training.iba.repository.ArtistRepository;
import com.training.iba.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private FestivalRepository festivalRepository;

    public List<Artist> getArtistsAvailable(Date date) {
        List<Artist> artistList = new ArrayList<>();
        for (Artist artist : artistRepository.findAll()) {
            artistList.add(artist);
        }
        for (Festival festival : festivalRepository.findAll()) {
            if (festival.getFestDate().equals(date)) {
                for (Artist artist : festival.getArtists()) {
                    artistList.remove(artist);
                }
            }
        }
        return artistList;
    }
}
