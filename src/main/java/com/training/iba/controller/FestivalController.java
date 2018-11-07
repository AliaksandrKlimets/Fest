package com.training.iba.controller;

import com.training.iba.entity.Festival;
import com.training.iba.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/festivals")
public class FestivalController {

    @Autowired
    private FestivalRepository festivalRepository;

    @GetMapping
    public Iterable<Festival> festivals() {
        return festivalRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Festival> getUser(@PathVariable Long id){
        return festivalRepository.findById(id);
    }
}
