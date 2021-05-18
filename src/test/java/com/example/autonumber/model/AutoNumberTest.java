package com.example.autonumber.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class AutoNumberTest {
    public AutoNumber autoNumber = new AutoNumber();

    @Test
    void getRandomNumber() {
        Assert.assertNotNull(autoNumber.getRandomNumber());
    }

    @Test
    void whenFixNumber() {
        autoNumber.setNumber(111);
        autoNumber.setText("ХХХ");
        Assert.assertNotEquals("Х111ХХ 116 RUS", autoNumber.getRandomNumber());
    }

    @Test
    void getNextNumber() {
        autoNumber.setNumber(999);
        autoNumber.setText("ХХХ");
        Assert.assertEquals("А001АА 116 RUS", autoNumber.getNextNumber());
    }
}