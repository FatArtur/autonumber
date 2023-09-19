package com.example.autonumber.sevice;

import com.example.autonumber.model.AutoNumber;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class NumberServiceImplTest {

    @Autowired
    private NumberServiceImpl numberService;

    @Test
    void getRandomNumber() {
        Assert.assertNotNull(numberService.getRandomNumber());
    }

    @Test
    void getFirstNumber() {
        Assert.assertEquals("А001АА 116 RUS", numberService.getNextNumber());
    }

    @Test
    void getNextNumber() {
        AutoNumber autoNumber = AutoNumber.builder().text("ВХХ").number(999).build();
        AutoNumber number = numberService.incrementNumber(autoNumber);
        Assert.assertEquals("Е001АА 116 RUS", numberService.generatedAutoNumber(number));
    }

    @Test
    void getNextNumber2() {
        AutoNumber autoNumber = AutoNumber.builder().text("ХХХ").number(999).build();
        AutoNumber number = numberService.incrementNumber(autoNumber);
        Assert.assertEquals("А001АА 116 RUS", numberService.generatedAutoNumber(number));
    }

    @Test
    void generatedNumber() {
        AutoNumber autoNumber = AutoNumber.builder().text("ВТХ").number(573).build();
        Assert.assertEquals("В573ТХ 116 RUS", numberService.generatedAutoNumber(autoNumber));
    }

    @Test
    void convertNumber_1() {
        Assert.assertEquals("003", numberService.convertNumberToString(3));
    }

    @Test
    void convertNumber_2() {
        Assert.assertEquals("010", numberService.convertNumberToString(10));
    }
}