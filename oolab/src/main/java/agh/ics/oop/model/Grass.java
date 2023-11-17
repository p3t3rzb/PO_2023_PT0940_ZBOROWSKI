package agh.ics.oop.model;

public class Grass implements WorldElement {
    final Vector2D position;
    public Grass(Vector2D position) {
        this.position = position;
    }

    public Vector2D getPosition() {
        return position;
    }

    public String toString() {
        return position.toString();
    }

    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }
}
