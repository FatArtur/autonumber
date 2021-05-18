package com.example.autonumber.model;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class AutoNumber {
    private String text;
    private int number;
    private final String REGION = " 116 RUS";
    private final List<Character> boxLetter = new ArrayList<>(Arrays
            .asList('А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'));
    private Random random = new Random();
    private static List<String> boxNumbers;

    static {
        boxNumbers = new ArrayList<>();
    }

    public String getRandomNumber() {
        randomValue();
        while (repeatAutoNumber(text, number)) {
            randomValue();
        }
        boxNumbers.add(text + number);
        return generatedAutoNumber(text, number);
    }

    private void randomValue() {
        number = random.nextInt(999) + 1;
        text = "" + boxLetter.get(random.nextInt(12)) + boxLetter.get(random.nextInt(12))
                + boxLetter.get(random.nextInt(12));
    }


    private String generatedAutoNumber(String text, int number) {
        String result = text.substring(0, 1) + convertNumberToString(number) + text.substring(1, 3) + REGION;
        return result;
    }

    private String convertNumberToString(int number) {
        String result;
        if (number < 10) {
            result = "00" + number;
        } else if (number < 100) {
            result = "0" + number;
        } else result = "" + number;
        return result;
    }

    private boolean repeatAutoNumber(String text, int val) {
        return boxNumbers.stream().anyMatch(s -> s.equals(text + val));
    }

    public String getNextNumber() {
        if (text == null) {
            text = "ААА";
            number++;
        }
        char[] letters = text.toCharArray();
        if (number < 999) {
            boxNumbers.add(text + number);
            return generatedAutoNumber(text, ++number);
        }
        if (number == 999) {
            number = 001;
            if (letters[2] != 'Х') {
                text = "" + letters[0] + letters[1] + boxLetter.get(boxLetter.indexOf(letters[2]) + 1);
                boxNumbers.add(text + number);
                return generatedAutoNumber(text, number);
            }
            if (letters[2] == 'Х') {
                if (letters[1] != 'Х') {
                    text = "" + letters[0] + boxLetter.get(boxLetter.indexOf(letters[1]) + 1)
                            + boxLetter.get(0);
                    boxNumbers.add(text + number);
                    return generatedAutoNumber(text, number);
                }
                if (letters[1] == 'Х') {
                    if (letters[0] != 'Х') {
                        text = "" + boxLetter.get(boxLetter.indexOf(letters[1]) + 1)
                                + boxLetter.get(0)
                                + boxLetter.get(0);
                        boxNumbers.add(text + number);
                        return generatedAutoNumber(text, number);
                    }
                    if (letters[0] == 'Х') {
                        text = "" + boxLetter.get(0)
                                + boxLetter.get(0)
                                + boxLetter.get(0);
                        boxNumbers.add(text + number);
                        return generatedAutoNumber(text, number);
                    }
                }
            }
        }
        boxNumbers.add(text + number);
        return generatedAutoNumber(text, number);
    }
}
