package agh.ics.projektC2.model;

public class Plant implements WorldElement {
    private final Vector2D position;

    public Plant(Vector2D position) {
        this.position = position;
    }

    @Override
    public String getImageFile() {
        return "plant.png";
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }
}
