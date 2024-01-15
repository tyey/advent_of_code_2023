package org.advent._23;

public enum Color {
    RED,
    GREEN,
    BLUE;

    public static Color fromString(String color) {
        switch(color) {
            case "RED": case "red":
                return RED;
            case "GREEN": case "green":
                return GREEN;
            case "BLUE": case "blue":
                return BLUE;
            default:
                throw new IllegalArgumentException("Invalid color: " + color);
        }
    }
}
