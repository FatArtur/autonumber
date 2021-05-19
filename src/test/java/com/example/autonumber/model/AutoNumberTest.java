package com.example.autonumber.model;

import com.example.autonumber.sevice.NumberService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class AutoNumberTest {
    public AutoNumber autoNumber = new AutoNumber();
    public NumberService numberService = new NumberService(autoNumber);

    @Test
    void getRandomNumber() {
        Assert.assertNotNull(numberService.getRandomNumber());
    }

    @Test
    void whenFixNumber() {
        autoNumber.number.set(111);
        autoNumber.setText("ХХХ");
        Assert.assertNotEquals("Х111ХХ 116 RUS", numberService.getRandomNumber());
    }

    @Test
    void getNextNumber() {
        autoNumber.number.set(999);
        autoNumber.setText("ХХХ");
        Assert.assertEquals("А001АА 116 RUS", numberService.getNextNumber());
    }

    @Test
    void getNextNumber_2() {
        autoNumber.number.set(999);
        autoNumber.setText("ВХХ");
        Assert.assertEquals("Е001АА 116 RUS", numberService.getNextNumber());
    }

    @Test
    void generatedNumber() {
        int number = 573;
        String txt = "ВТХ";
        Assert.assertEquals("В573ТХ 116 RUS", autoNumber.generatedAutoNumber(txt, number));
    }

    @Test
    void convertNumber_1() {
        Assert.assertEquals("003", autoNumber.convertNumberToString(3));
    }

    @Test
    void convertNumber_2() {
        Assert.assertEquals("010", autoNumber.convertNumberToString(10));
    }

}