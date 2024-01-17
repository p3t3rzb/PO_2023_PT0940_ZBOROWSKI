package agh.ics.projektC2.model;

public interface WorldElement {
    boolean isAt(Vector2D position);

    Vector2D getPosition();

    String getImageFile();
}
