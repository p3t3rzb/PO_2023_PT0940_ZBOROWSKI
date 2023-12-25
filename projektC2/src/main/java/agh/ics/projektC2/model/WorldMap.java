package agh.ics.projektC2.model;

import java.util.List;

public interface WorldMap extends MoveValidator {
    int getID();

    void place(Animal animal) throws PositionAlreadyOccupiedException;

    void move(Animal animal, MoveDirection direction);

    boolean isOccupied(Vector2D position);

    WorldElement objectAt(Vector2D position);

    Boundary getCurrentBounds();

    List<WorldElement> getElements();
}
