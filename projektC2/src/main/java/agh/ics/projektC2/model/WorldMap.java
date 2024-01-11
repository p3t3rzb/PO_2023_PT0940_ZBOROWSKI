package agh.ics.projektC2.model;

import java.util.HashMap;
import java.util.List;

public interface WorldMap extends MoveValidator {
    int getID();

    void addPlants(int count);

    void place(Animal animal) throws PositionAlreadyOccupiedException;

    void move();

    void procreate();

    void eatPlants();

    void removeDeadAnimals();

    boolean isOccupied(Vector2D position);

    WorldElement objectAt(Vector2D position);

    HashMap<Vector2D, Boolean> getForbiddenForAnimals();

    Boundary getCurrentBounds();

    List<WorldElement> getElements();

    List<Animal> getAnimals();
}
