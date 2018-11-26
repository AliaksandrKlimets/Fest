package com.training.iba.controller;


import com.training.iba.service.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class ActivationController {

    @Autowired
    private FestivalService festivalService;

    @GetMapping("/activate/{code}")
    public Map<String,String> activate(@PathVariable("code") String code){
        Map<String,String> map =  new HashMap<>();
        boolean isActive = festivalService.confirmAttention(code);
        if(isActive) {
            map.put("Attention", "Yes");
        }else return null;
        return map;
    }


}
