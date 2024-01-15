package org.advent._23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2App {

    public static void main(String[] args) throws IOException {
        Part1And2 part1 = new Part1And2(12, 13, 14);
        part1.loadInput();
        long sum = part1.validateGames();
        System.out.println("sum of valid games id = " + sum);

        long power = part1.minimumSetPowersSum();
        System.out.println("sum of minimum set powers = " + power);
    }

    private static BufferedReader getBufferedReader() {
        Class currentClass = new Object() {}.getClass().getEnclosingClass();
        InputStream inputStream = currentClass.getClassLoader().getResourceAsStream("input.txt");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        return reader;
    }

    public static class Part1And2 {
        private final int red;
        private final int green;
        private final int blue;
        private BufferedReader reader = getBufferedReader();
        ;

        private List<Game> games = new ArrayList<>();

        public Part1And2(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public Game parseLine(String input) {
            Pattern pattern = Pattern.compile("Game (\\d+): (.+)");
            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                int gameId = Integer.parseInt(matcher.group(1));
                Map<Integer, Map<Color, Integer>> rounds = parseColors(matcher.group(2));

                return new Game(gameId, rounds);
            } else {
                throw new IllegalArgumentException("Invalid input format");
            }
        }

        private Map<Integer, Map<Color,Integer>> parseColors(String colorsString) {
            Map<Integer, Map<Color,Integer>> rounds = new HashMap<>();
            String[] groups = colorsString.split(";");

            int groupCount = 1;
            for (String group : groups) {

                group = group.trim();
                String[] parts = group.split(",");

                Map<Color, Integer> colors = getColorIntegerMap(parts);
                rounds.put(groupCount++, colors);
            }

            return rounds;
        }

        private static Map<Color, Integer> getColorIntegerMap(String[] parts) {
            Map<Color, Integer> colors = new HashMap<>();
            for (int i = 0; i < parts.length; i++) {
                String[] pair = parts[i].trim().split("\\s+");
                if(pair.length != 2) {
                    throw new IllegalArgumentException("Too many arguments in color pair: " + parts[i]);
                }
                int quantity = Integer.parseInt(pair[0]);
                Color color = Color.fromString(pair[1].toLowerCase());

                colors.put(color, quantity);
            }
            return colors;
        }

        public void loadInput() throws IOException {
            for (String line; (line = reader.readLine()) != null; ) {
                Game game = parseLine(line);
                games.add(game);
            }
        }

        public long minimumSetPowersSum() {
            long sum = 0;
            for (Game game : games) {
                sum += minimumSetPower(game);
            }
            return sum;
        }

        private long minimumSetPower(Game game) {
            Collection<Map<Color, Integer>> values = game.rounds.values();
            int maxRed = 0;
            int maxGreen = 0;
            int maxBlue = 0;
            for (Map<Color, Integer> value : values) {
                maxRed = value.getOrDefault(Color.RED, 0)>maxRed?value.getOrDefault(Color.RED, 0):maxRed;
                maxGreen = value.getOrDefault(Color.GREEN, 0)>maxGreen?value.getOrDefault(Color.GREEN, 0):maxGreen;
                maxBlue = value.getOrDefault(Color.BLUE, 0)>maxBlue?value.getOrDefault(Color.BLUE, 0):maxBlue;
            }
            return maxRed * maxGreen * maxBlue;
        }

        public long validateGames() {
            long validGamesSum = 0;
            for (Game game : games) {
                if (validateGame(game)) {
                    validGamesSum += game.gameId;
                }
            }
            return validGamesSum;
        }

        private boolean validateGame(Game game) {
            Set<Map.Entry<Integer, Map<Color, Integer>>> entries = game.rounds.entrySet();
            Optional<Map.Entry<Integer, Map<Color, Integer>>> first = entries.stream().filter((e) -> {
                int redQuantity = e.getValue().getOrDefault(Color.RED, 0);
                int greenQuantity = e.getValue().getOrDefault(Color.GREEN, 0);
                int blueQuantity = e.getValue().getOrDefault(Color.BLUE, 0);
                return !(redQuantity <= red && greenQuantity <= green && blueQuantity <= blue);
            }).findFirst();
            return first.isEmpty();
        }

        private class Game {
            int gameId;
            private final Map<Integer, Map<Color, Integer>> rounds;


            public Game(int gameId, Map<Integer, Map<Color, Integer>> rounds) {
                this.gameId = gameId;
                this.rounds = rounds;
            }

            @Override
            public String toString() {
                return "Game " + gameId + ": " + rounds;
            }
        }
    }


}