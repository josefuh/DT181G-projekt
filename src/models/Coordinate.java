package models;

import java.awt.*;
import java.util.Objects;

public class Coordinate {

    private int x;
    private int y;
    private String t;
    private Color color;

    public Coordinate(int x,int y,String t) {
        this.x = x;
        this.y = y;
        this.t = t;
    }
    public void setColor(Color c) {
        this.color = c;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object anotherObject) {
        if(anotherObject.getClass().equals(this.getClass())) {
            Coordinate other = ((Coordinate) anotherObject);
            return x == other.x && y == other.y;
        } else {
            return false;
        }
    }
}
