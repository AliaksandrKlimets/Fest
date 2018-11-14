package com.training.iba.service;

import com.training.iba.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FestivalService {

    @Autowired
    private FestivalRepository festivalRepository;


}
