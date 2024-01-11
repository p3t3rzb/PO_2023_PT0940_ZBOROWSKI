package agh.ics.oop.model;

public class Grass implements WorldElement {
    private final Vector2D position;
    public Grass(Vector2D position) {
        this.position = position;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public String getImageFile() {
        return "grass.png";
    }

    public String toString() {
        return "*";
    }

    @Override
    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }
}