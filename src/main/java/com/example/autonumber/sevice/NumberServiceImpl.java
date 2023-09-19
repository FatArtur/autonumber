package com.example.autonumber.sevice;

import com.example.autonumber.model.AutoNumber;
import com.example.autonumber.model.Region;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

import static com.example.autonumber.helper.Helper.*;


@Service
@Data
public class NumberServiceImpl implements NumberService {

    private static AtomicReference<AutoNumber> autoNumber;
    private static Set<String> boxNumbers;

    static {
        autoNumber = new AtomicReference<>(AutoNumber.builder().build());
        boxNumbers = new CopyOnWriteArraySet<>();
    }

    @Override
    public String getRandomNumber() {
        return getNumber((number) -> {
            AutoNumber newNumber;
            do {
                newNumber = makeRandomNumber();
            } while (boxNumbers.contains(newNumber.getText() + newNumber.getNumber()));
            return newNumber;
        });
    }

    private boolean checkSizeBox() {
        return boxNumbers.size() == 1726272;
    }

    private String getNumber(UnaryOperator<AutoNumber> operator) {
        if (checkSizeBox()) {
            return ERROR_IS_END;
        }
        AutoNumber oldNumber;
        AutoNumber newNumber;
        boolean success;
        do {
            oldNumber = autoNumber.get();
            newNumber = operator.apply(oldNumber);
            success = autoNumber.compareAndSet(oldNumber, newNumber);
        } while (!success);

        boxNumbers.add(newNumber.getText() + newNumber.getNumber());
        return generatedAutoNumber(newNumber);
    }

    private AutoNumber makeRandomNumber() {
        Random random = new Random();
        int number = random.nextInt(999) + 1;
        String text = ""
                + BOX_OF_LETTER.get(random.nextInt(12))
                + BOX_OF_LETTER.get(random.nextInt(12))
                + BOX_OF_LETTER.get(random.nextInt(12));
        return AutoNumber.builder()
                .number(number)
                .text(text)
                .build();
    }


    @Override
    public String getNextNumber() {
        return getNumber((number) -> {
            AutoNumber newNumber = incrementNumber(number);
            checkDoubleNumber(newNumber);
            return newNumber;
        });
    }

    protected AutoNumber incrementNumber(AutoNumber autoNumber) {
        AutoNumber newNumber = AutoNumber.builder().build();
        if (autoNumber.getText() == null) {
            newNumber.setText("ААА");
            newNumber.setNumber(1);
            return newNumber;
        }

        if (autoNumber.getNumber() < 999) {
            newNumber.setText(autoNumber.getText());
            newNumber.setNumber(autoNumber.getNumber() + 1);
            return newNumber;
        }

        if (autoNumber.getNumber() == 999) {
            String text = autoNumber.getText();
            newNumber.setNumber(1);
            if (text.charAt(2) != 'Х') {
                newNumber.setText("" + text.charAt(0) + text.charAt(1)
                        + BOX_OF_LETTER.get(BOX_OF_LETTER.indexOf(text.charAt(2)) + 1));
                return newNumber;
            }
            if (text.charAt(2) == 'Х') {
                if (text.charAt(1) != 'Х') {
                    newNumber.setText("" + text.charAt(0)
                            + BOX_OF_LETTER.get(BOX_OF_LETTER.indexOf(text.charAt(1)) + 1)
                            + BOX_OF_LETTER.get(0));
                    return newNumber;
                }
                if (text.charAt(1) == 'Х') {
                    if (text.charAt(0) != 'Х') {
                        newNumber.setText(""
                                + BOX_OF_LETTER.get(BOX_OF_LETTER.indexOf(text.charAt(0)) + 1)
                                + BOX_OF_LETTER.get(0)
                                + BOX_OF_LETTER.get(0));
                        return newNumber;
                    }
                    if (text.charAt(0) == 'Х') {
                        newNumber.setText(""
                                + BOX_OF_LETTER.get(0)
                                + BOX_OF_LETTER.get(0)
                                + BOX_OF_LETTER.get(0));
                        return newNumber;
                    }
                }
            }
        }
        throw new ArithmeticException(ERROR_UNEXPECTED);
    }

    private void checkDoubleNumber(AutoNumber newNumber) {
        if (boxNumbers.contains(newNumber.getText() + newNumber.getNumber())) {
            incrementNumber(newNumber);
            checkDoubleNumber(newNumber);
        }
    }

    protected String generatedAutoNumber(AutoNumber autoNumber) {
        return autoNumber.getText().charAt(0) + convertNumberToString(autoNumber.getNumber()) +
                autoNumber.getText().substring(1, 3) + " " + Region.TATARSTAN.getNumber();
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
