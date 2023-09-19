package com.example.autonumber.model;

public enum Region {
    TATARSTAN("116 RUS");

    private final String number;

    Region(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
