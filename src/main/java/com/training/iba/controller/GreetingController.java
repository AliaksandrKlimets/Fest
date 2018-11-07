package com.training.iba.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GreetingController {

    @GetMapping("/")
    public Map<String,String> hello(){
        Map<String,String> map =  new HashMap<>();
        map.put("Hello", "User");
        return map;
    }

}
