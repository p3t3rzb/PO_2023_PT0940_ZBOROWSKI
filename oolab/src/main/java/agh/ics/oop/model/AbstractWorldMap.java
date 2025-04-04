package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2D,Animal> animals = new HashMap<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);
    private final List<MapChangeListener> observers = new ArrayList<>();
    protected final int mapID;
    private static int objectsCount = 0;

    abstract public Boundary getCurrentBounds();

    public AbstractWorldMap() {
        mapID = objectsCount;
        objectsCount++;
    }

    public int getID() {
        return mapID;
    }

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
    public List<Animal> getOrderedAnimals() {
        return animals.values().stream()
                .sorted(Comparator.comparing(
                                Animal::getPosition, Comparator.comparingInt(Vector2D::getX))
                        .thenComparing(Animal::getPosition, Comparator.comparingInt(Vector2D::getY)))
                .collect(Collectors.toList());
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
        if(!temp.equals(animal.getPosition().toString())) {
            mapChanged("Moved an animal from " + temp + " to " + animal.getPosition().toString());
        } else {
            mapChanged("Animal at " + temp + " rotating");
        }

    }

    @Override
    public boolean isOccupied(Vector2D position)  {
        return (objectAt(position) != null);
    }

    @Override
    abstract public Optional<WorldElement> objectAt(Vector2D position);

    @Override
    abstract public boolean canMoveTo(Vector2D position);

    @Override
    public List<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }
}
