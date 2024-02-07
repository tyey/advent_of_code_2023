package org.advent._23;

import java.util.Objects;

public class Pair {
    private final int index;
    private final int value;

    public Pair(int index, int value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return index == pair.index && value == pair.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, value);
    }
}
