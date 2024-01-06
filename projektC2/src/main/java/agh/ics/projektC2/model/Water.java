package agh.ics.projektC2.model;

public class Water implements WorldElement {
    private final Vector2D position;

    public Water(Vector2D position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "~";
    }

    @Override
    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }
}
