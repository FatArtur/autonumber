package com.example.autonumber.model;


import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Data
public class AutoNumber {
    private volatile String text;
    public AtomicInteger number = new AtomicInteger();
    private final String REGION = " 116 RUS";
    private final List<Character> boxLetter = new ArrayList<>(Arrays
            .asList('А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'));


    public String generatedAutoNumber(String text, int number) {
        String result = text.substring(0, 1) + convertNumberToString(number) + text.substring(1, 3) + REGION;
        return result;
    }

    protected String convertNumberToString(int number) {
        String result;
        if (number < 10) {
            result = "00" + number;
        } else if (number < 100) {
            result = "0" + number;
        } else result = "" + number;
        return result;
    }
}
