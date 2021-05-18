package com.example.autonumber.controller;


import com.example.autonumber.model.AutoNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("number")
public class NumberController {
    private AutoNumber autoNumber;

    @Autowired
    public NumberController(AutoNumber autoNumber) {
        this.autoNumber = autoNumber;
    }

    @GetMapping("random")
    public String random (){
        return autoNumber.getRandomNumber();
    }

    @GetMapping("next")
    public String next (){
        return autoNumber.getNextNumber();
    }
}
