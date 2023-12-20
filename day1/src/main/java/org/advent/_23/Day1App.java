package org.advent._23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Day1App {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Day1App.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("input.txt");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        long sum = 0;
        for (String line; (line = reader.readLine()) != null;) {
            int number = getNumber(line);
            sum += number;
        }
        System.out.println("sum = " + sum);
    }

    private static int getNumber(String line) {
        Character firstDigit = null;
        Character lastDigit = null;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (Character.isDigit(c)) {
                if (firstDigit == null) {
                    firstDigit = c;
                }
                lastDigit = c;
            }
        }
        if(firstDigit == null) {
            throw new RuntimeException("No digit found in line: " + line);
        }
        return Integer.parseInt(firstDigit.toString()+ lastDigit);
    }
}