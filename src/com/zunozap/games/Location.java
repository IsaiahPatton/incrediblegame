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
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location))
            return false;
        Location l = (Location) o;
        return l.x == this.x && l.y == this.y;
    }

}