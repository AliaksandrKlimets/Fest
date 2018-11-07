package com.training.iba.controller;

import com.training.iba.entity.Festival;
import com.training.iba.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/festivals")
public class FestivalController {

    @Autowired
    private FestivalRepository festivalRepository;

    @GetMapping
    public Iterable<Festival> festivals() {
        return festivalRepository.findAll();
    }
}
