package org.advent._23;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day1AppTest {

    public void test() {
        String input="jb5eighthree";


        String stringToDigit = Day1App.convertStringToDigit(input);

        assertTrue("jb583".equals(stringToDigit), "stringToDigit = " + stringToDigit);

        int number = Day1App.getNumber(stringToDigit);

        assertTrue(53==number, "number = " + number);
    }

}