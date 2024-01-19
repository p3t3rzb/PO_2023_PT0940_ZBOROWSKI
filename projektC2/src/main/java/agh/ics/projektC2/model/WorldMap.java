package agh.ics.projektC2.model;

import java.util.List;
import java.util.Set;

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

    Set<Animal> getDeadAnimals();

    WorldElement objectAt(Vector2D position);

    Set<Vector2D> getForbiddenForAnimals();

    Boundary getCurrentBounds();

    List<Animal> getAnimals();

    List<Plant> getPlants();
}
