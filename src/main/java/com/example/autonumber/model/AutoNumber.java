package com.example.autonumber.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AutoNumber {
    private String text;
    private int number;

}
