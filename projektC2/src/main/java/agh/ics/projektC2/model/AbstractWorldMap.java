package agh.ics.projektC2.model;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected final AnimalMultiMap animals = new AnimalMultiMap();
    protected final HashMap<Vector2D,Plant> plants = new HashMap<>();
    protected final HashMap<Vector2D,Boolean> forbiddenForPlants = new HashMap<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);
    private final List<MapChangeListener> observers = new ArrayList<>();
    protected final int mapID;
    protected final int plantEnergy;
    private static int objectsCount = 0;
    protected MoveTransformation transformation;
    protected List<Vector2D> preferredPositions;
    protected List<Vector2D> notPreferredPositions;

    abstract public Boundary getCurrentBounds();

    @Override
    abstract public void eatPlants();

    @Override
    abstract public void removeDeadAnimals();

    public AbstractWorldMap(int plantEnergy) {
        this.plantEnergy = plantEnergy;
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
    public void addPlants(int count) {
        List<Vector2D> positions = new RandomPositionGenerator(preferredPositions,notPreferredPositions,count).getPositions();

        for(Vector2D position : positions) {
            plants.put(position,new Plant(position));
            forbiddenForPlants.put(position,Boolean.TRUE);
        }

        mapChanged(count + " new plants"); // usunąć później
    }

    @Override
    public void place(Animal animal) throws PositionAlreadyOccupiedException {
        if(!canMoveTo(animal.getPosition())) {
            throw new PositionAlreadyOccupiedException(animal.getPosition());
        }

        animals.put(animal.getPosition(),animal);
        mapChanged("Put an animal at " + animal.getPosition().toString());
    }

    @Override
    public void move(Animal animal) {
        if(!animals.get(animal.getPosition()).contains(animal)) { // zamiana na animal
            return;
        }
        String temp = animal.getPosition().toString();
        animals.remove(animal.getPosition(),animal);
        animal.move(this,transformation);
        animals.put(animal.getPosition(),animal);
        animal.setEnergy(animal.getEnergy()-1);
        if(!temp.equals(animal.getPosition().toString())) {
            mapChanged("Moved an animal from " + temp + " to " + animal.getPosition().toString());
        } else {
            mapChanged("Animal at " + temp + " rotating");
        }
    }

    public Plant plantAt(Vector2D position) {
        return plants.get(position);
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
        // uzupełnić później
        //result.addAll(animals.values());

        return result;
    }
}