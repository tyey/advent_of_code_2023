package org.advent._23;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Day1App {

    static List<String> stringDigits = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Day1App.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("input.txt");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        long sum = 0;
        for (String line; (line = reader.readLine()) != null;) {
            String lineWithDigits = convertStringToDigit(line);
            int number = getNumber(lineWithDigits);
            sum += number;
        }
        System.out.println("sum = " + sum);
        part2();
    }

    private static void part2() {
        long sum=0;
        try{
            File inputfile=new File(Day1App.class.getClassLoader().getResource("input.txt").toURI());
            BufferedReader br=new BufferedReader(new FileReader(inputfile));
            sum=br.lines().map(s->s.replaceAll("one","o1ne").replaceAll("two","t2wo")
                            .replaceAll("three","t3hree").replaceAll("four","f4our").replaceAll("five","f5ive")
                            .replaceAll("six","s6ix").replaceAll("seven","s7even").replaceAll("eight","e8ight")
                            .replaceAll("nine","n9ine").replaceAll("[a-z]",""))
                    .mapToInt(s->(s.charAt(0)-'0')*10+s.charAt(s.length()-1)-'0').sum();
            br.close();
        }catch(Exception e){System.out.println(e.toString());}
        System.out.println("Part2: " +sum);
    }

    static String convertStringToDigit(String line) {
        String lineWithDigits= line;
        for (int i = 1; i < 10; i++) {
            lineWithDigits = lineWithDigits.replaceAll(stringDigits.get(i - 1), String.valueOf(i));
        }
        return lineWithDigits;
    }

    static int getNumber(String line) {
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