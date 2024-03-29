package com.example.autonumber.controller;


import com.example.autonumber.sevice.NumberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("number")
public class NumberController {
    private final NumberServiceImpl numberService;

    @Autowired
    public NumberController(NumberServiceImpl numberService) {
        this.numberService = numberService;
    }

    @GetMapping("random")
    public String random() {
        return numberService.getRandomNumber();
    }

    @GetMapping("next")
    public String next() {
        return numberService.getNextNumber();
    }
}
