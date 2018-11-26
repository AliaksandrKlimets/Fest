package com.training.iba.repository;

import com.training.iba.entity.Festival;
import com.training.iba.entity.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FestivalRepository extends CrudRepository<Festival, Long> {
    Festival findById(long id);
    List<Festival> findByGenres(Genres genre);
}
