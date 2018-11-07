package com.training.iba.repository;

import com.training.iba.entity.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
}
