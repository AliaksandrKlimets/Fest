package com.training.iba.repository;

import com.training.iba.entity.Artist;
import com.training.iba.entity.Genres;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    Artist findById(long id);
    List<Artist> findByGenres(Genres genres);
}
