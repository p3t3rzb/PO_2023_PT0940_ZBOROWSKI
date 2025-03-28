package agh.ics.oop.model;

public class Vector2D {
    private final int x, y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return '(' + String.valueOf(x) + ',' + String.valueOf(y) + ')';
    }

    public boolean precedes(Vector2D other) {
        return x <= other.getX() && y <= other.getY();
    }

    public boolean follows(Vector2D other) {
        return x >= other.getX() && y >= other.getY();
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(other.getX()+x,other.getY()+y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(x-other.getX(),y-other.getY());
    }

    public Vector2D upperRight(Vector2D other) {
        return new Vector2D(Math.max(x,other.getX()),Math.max(y,other.getY()));
    }

    public Vector2D lowerLeft(Vector2D other) {
        return new Vector2D(Math.min(x,other.getX()),Math.min(y,other.getY()));
    }

    public Vector2D opposite() {
        return new Vector2D(-x,-y);
    }

    @Override
    public int hashCode() {
        return 1000*x+y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if(!(other instanceof Vector2D))
            return false;
        Vector2D that = (Vector2D) other;
        return that.getX() == x && that.getY() == y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
