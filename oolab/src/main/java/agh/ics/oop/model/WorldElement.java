package agh.ics.oop.model;

public interface WorldElement {
    public boolean isAt(Vector2D position);

    public Vector2D getPosition();

    public String getImageFile();
}
