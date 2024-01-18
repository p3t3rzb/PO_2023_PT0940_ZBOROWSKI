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

    void addObserver(MapChangeListener mapChangeListener);

    void removeObserver(MapChangeListener mapChangeListener);

    Boundary getPreferredBoundary();

    List<Animal> getDeadAnimals();

    WorldElement objectAt(Vector2D position);

    HashMap<Vector2D, Boolean> getForbiddenForAnimals();

    Boundary getCurrentBounds();

    List<Animal> getAnimals();

    List<Plant> getPlants();
}
