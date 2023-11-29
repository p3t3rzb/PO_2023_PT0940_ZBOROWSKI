package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2D,Animal> animals = new HashMap<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);

    abstract public Boundary getCurrentBounds();

    public String toString() {
        Boundary currentBounds = getCurrentBounds();
        return visualizer.draw(currentBounds.bottomLeftCorner(),currentBounds.upperRightCorner());
    }

    @Override
    public void place(Animal animal) throws PositionAlreadyOccupiedException {
        if(canMoveTo(animal.getPosition()) == false) {
            throw new PositionAlreadyOccupiedException(animal.getPosition());
        }

        animals.put(animal.getPosition(),animal);
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        if(isOccupied(animal.getPosition()) == false) {
            return;
        }
        animals.remove(animal.getPosition());
        animal.move(direction,this);
        animals.put(animal.getPosition(),animal);
    }

    @Override
    public boolean isOccupied(Vector2D position)  {
        return (objectAt(position) != null);
    }

    @Override
    abstract public WorldElement objectAt(Vector2D position);

    @Override
    abstract public boolean canMoveTo(Vector2D position);

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> result = new ArrayList<>();

        result.addAll(animals.values());

        return result;
    }
}
