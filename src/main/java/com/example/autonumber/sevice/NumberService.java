package com.example.autonumber.sevice;

import com.example.autonumber.model.AutoNumber;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


@Service
@Data
public class NumberService {
    private AutoNumber autoNumber;
    private static List<String> boxNumbers;

    @Autowired
    public NumberService(AutoNumber autoNumber) {
        this.autoNumber = autoNumber;
        boxNumbers = new CopyOnWriteArrayList<>();
    }


    public String getRandomNumber() {
        Random random = new Random();
        while (true) {
            if (boxNumbers.size() == 1726272) {
                return "AutoNumber is end";
            }
            int number = random.nextInt(999) + 1;
            String text = "" + autoNumber.getBoxLetter().get(random.nextInt(12))
                    + autoNumber.getBoxLetter().get(random.nextInt(12))
                    + autoNumber.getBoxLetter().get(random.nextInt(12));
            if (!boxNumbers.stream().anyMatch(s -> s.equals(text + number))) {
                boxNumbers.add(text + number);
                autoNumber.setText(text);
                autoNumber.number.set(number);
                return autoNumber.generatedAutoNumber(text, number);
            }
        }
    }


    public String getNextNumber() {
        if (autoNumber.getText() == null) {
            autoNumber.setText("ААА");
            autoNumber.number.set(0);
        }
        char[] letters = autoNumber.getText().toCharArray();
        if (autoNumber.getNumber().get() < 999) {
            boxNumbers.add(autoNumber.getText() + autoNumber.number.incrementAndGet());
            return autoNumber.generatedAutoNumber(autoNumber.getText(), autoNumber.number.get());
        }
        if (autoNumber.getNumber().get() == 999) {
            autoNumber.number.set(1);
            if (letters[2] != 'Х') {
                autoNumber.setText("" + letters[0] + letters[1]
                        + autoNumber.getBoxLetter().get(autoNumber.getBoxLetter().indexOf(letters[2]) + 1));
                boxNumbers.add(autoNumber.getText() + autoNumber.number.get());
                return autoNumber.generatedAutoNumber(autoNumber.getText(), autoNumber.number.get());
            }
            if (letters[2] == 'Х') {
                if (letters[1] != 'Х') {
                    autoNumber.setText("" + letters[0] +
                            autoNumber.getBoxLetter().get(autoNumber.getBoxLetter().indexOf(letters[1]) + 1)
                            + autoNumber.getBoxLetter().get(0));
                    boxNumbers.add(autoNumber.getText() + autoNumber.number.get());
                    return autoNumber.generatedAutoNumber(autoNumber.getText(), autoNumber.number.get());
                }
                if (letters[1] == 'Х') {
                    if (letters[0] != 'Х') {
                        autoNumber.setText("" + autoNumber.getBoxLetter()
                                .get(autoNumber.getBoxLetter().indexOf(letters[0]) + 1)
                                + autoNumber.getBoxLetter().get(0)
                                + autoNumber.getBoxLetter().get(0));
                        boxNumbers.add(autoNumber.getText() + autoNumber.number.get());
                        return autoNumber.generatedAutoNumber(autoNumber.getText(), autoNumber.number.get());
                    }
                    if (letters[0] == 'Х') {
                        autoNumber.setText("" + autoNumber.getBoxLetter().get(0)
                                + autoNumber.getBoxLetter().get(0)
                                + autoNumber.getBoxLetter().get(0));
                        boxNumbers.add(autoNumber.getText() + autoNumber.number.get());
                        return autoNumber.generatedAutoNumber(autoNumber.getText(), autoNumber.number.get());
                    }
                }
            }
        }
        return "It's unknown error";
    }

}
