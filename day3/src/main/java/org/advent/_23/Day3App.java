package org.advent._23;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;

public class Day3App {

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        System.out.println("part1 data loaded");
        long sum1 = part1.calculatePartNumbersSum();
        System.out.println("sum of valid part numbers = " + sum1);

        Part2 part2 = new Part2();
        System.out.println("part 2 data loaded");
        long sum2 = part2.sumGearRatios();
        System.out.println("sum of valid part numbers = " + sum2);

    }

    private static BufferedReader getBufferedReader() {
        Class currentClass = new Object() {
        }.getClass().getEnclosingClass();
        InputStream inputStream = currentClass.getClassLoader().getResourceAsStream("input");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        return reader;
    }

    public static class Part1 {
        private List<String> lines = new ArrayList<>();

        private List<Point> symbols = new ArrayList<>();
        private BufferedReader reader = getBufferedReader();
        private Map<Integer, Set<Pair>> numbersPerLine = new HashMap<>();

        public Part1() throws IOException {
            loadInput();
            loadSymbolCords();
            findPartNumbers();
        }

        private void loadSymbolCords() throws IOException {
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                for (int j = 0; j < line.length(); j++) {
                    Character c = line.charAt(j);
                    if (!(Character.isDigit(c) || (c == '.'))) {
                        symbols.add(new Point(j, i));
                    }
                }
            }
        }

        private void loadInput() throws IOException {
            for (String line; (line = reader.readLine()) != null; ) {
                lines.add(line);
            }
        }

        public long calculatePartNumbersSum() {
            long sum = 0;
            for (Integer lineIndex : numbersPerLine.keySet()) {
                Set<Pair> pairs = numbersPerLine.get(lineIndex);
                for (Pair pair : pairs) {
                    sum += pair.getValue();
                }
            }
            return sum;
        }

        private void findPartNumbers() {
            for (Point p : symbols) {
                int xmin = p.x - 1 >= 0 ? p.x - 1 : 0;
                int xmax = p.x + 1 < lines.get(p.y).length() ? p.x + 1 : lines.get(p.y).length() - 1;
                int ymin = p.y - 1 >= 0 ? p.y - 1 : 0;
                int ymax = p.y + 1 < lines.size() ? p.y + 1 : lines.size() - 1;

                for (int y = ymin; y <= ymax; y++) {
                    String line = lines.get(y);
                    for (int x = xmin; x <= xmax; x++) {
                        Character c = line.charAt(x);
                        if (Character.isDigit(c)) {
                            Pair pair = findNumberAtIndex(x, line);
                            if (!numbersPerLine.containsKey(y)) {
                                numbersPerLine.put(y, new HashSet<>());
                            }
                            numbersPerLine.get(y).add(pair);
                        }
                    }
                }

            }
        }

        private Pair findNumberAtIndex(int x, String line) {
            StringBuilder numberBuilder = new StringBuilder();
            int cursor = x;
            while (cursor - 1 >= 0 && Character.isDigit(line.charAt(cursor - 1))) {
                cursor--;
            }
            int index = cursor;
            while (cursor < line.length() && Character.isDigit(line.charAt(cursor))) {
                numberBuilder.append(line.charAt(cursor));
                cursor++;
            }
            if (numberBuilder.length() == 0) {
                throw new IllegalArgumentException("No number found at index " + x + " in line " + line);
            }
            return new Pair(index, Integer.parseInt(numberBuilder.toString()));
        }
    }

    public static class Part2 {
        private List<String> lines = new ArrayList<>();

        private List<Point> symbols = new ArrayList<>();
        private BufferedReader reader = getBufferedReader();
        private List<Long> gearRatios = new ArrayList<Long>();

        public Part2() throws IOException {
            loadInput();
            loadSymbolCords();
            findGearRatios();
        }

        private void loadSymbolCords() throws IOException {
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                for (int j = 0; j < line.length(); j++) {
                    Character c = line.charAt(j);
                    if ((c == '*')) {
                        symbols.add(new Point(j, i));
                    }
                }
            }
        }

        private void loadInput() throws IOException {
            for (String line; (line = reader.readLine()) != null; ) {
                lines.add(line);
            }
        }

        public long sumGearRatios() {
            long sum = 0;
            for (Long gearRatio : gearRatios) {
                sum += gearRatio;
            }
            return sum;
        }

        private void findGearRatios() {
            for (Point p : symbols) {
                Set<Pair> pairs = new HashSet<>();
                int xmin = p.x - 1 >= 0 ? p.x - 1 : 0;
                int xmax = p.x + 1 < lines.get(p.y).length() ? p.x + 1 : lines.get(p.y).length() - 1;
                int ymin = p.y - 1 >= 0 ? p.y - 1 : 0;
                int ymax = p.y + 1 < lines.size() ? p.y + 1 : lines.size() - 1;

                for (int y = ymin; y <= ymax; y++) {
                    String line = lines.get(y);
                    for (int x = xmin; x <= xmax; x++) {
                        Character c = line.charAt(x);
                        if (Character.isDigit(c)) {
                            Pair pair = findNumberAtIndex(x, line);
                            pairs.add(pair);
                        }
                    }
                }
                if (2 == pairs.size()) {
                    long gearRatio = 1;
                    for (Pair pair : pairs) {
                        gearRatio *= pair.getValue();
                    }
                    gearRatios.add(gearRatio);
                }
            }
        }

        private Pair findNumberAtIndex(int x, String line) {
            StringBuilder numberBuilder = new StringBuilder();
            int cursor = x;
            while (cursor - 1 >= 0 && Character.isDigit(line.charAt(cursor - 1))) {
                cursor--;
            }
            int index = cursor;
            while (cursor < line.length() && Character.isDigit(line.charAt(cursor))) {
                numberBuilder.append(line.charAt(cursor));
                cursor++;
            }
            if (numberBuilder.length() == 0) {
                throw new IllegalArgumentException("No number found at index " + x + " in line " + line);
            }
            return new Pair(index, Integer.parseInt(numberBuilder.toString()));
        }
    }
}