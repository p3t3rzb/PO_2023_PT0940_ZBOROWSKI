package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2D,Animal> animals = new HashMap<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);
    private final List<MapChangeListener> observers = new ArrayList<>();

    abstract protected Boundary getCurrentBounds();

    public void addObserver(MapChangeListener listener) {
        observers.add(listener);
    }

    public void removeObserver(MapChangeListener listener) {
        observers.remove(listener);
    }

    private void mapChanged(String message) {
        for(MapChangeListener observer : observers) {
            observer.mapChanged(this,message);
        }
    }

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
        mapChanged("Put an animal at " + animal.getPosition().toString());
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        if(isOccupied(animal.getPosition()) == false) {
            return;
        }
        String temp = animal.getPosition().toString();
        animals.remove(animal.getPosition());
        animal.move(direction,this);
        animals.put(animal.getPosition(),animal);
        mapChanged("Moved an animal from " + temp + " to " + animal.getPosition().toString());
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
