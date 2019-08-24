package com.zunozap.games;

public class Location {

    public int x, y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + "-" + y;
    }

}