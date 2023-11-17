package agh.ics.oop.model;

public interface WorldElement {
    public String toString();
    public boolean isAt(Vector2D position);
    public Vector2D getPosition();
}
