package agh.ics.projektC2.model;

import javax.swing.text.Position;
import java.util.*;

import static java.util.Collections.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected final AnimalMultiMap animals = new AnimalMultiMap();
    protected final HashMap<Vector2D,Plant> plants = new HashMap<>();
    protected final HashMap<Vector2D,Boolean> forbiddenForPlants = new HashMap<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);
    private final List<MapChangeListener> observers = new ArrayList<>();
    protected final int mapID;
    protected final int plantEnergy;
    protected Mutation mutation;
    private static int objectsCount = 0;
    private final int satisfactoryEnergy; // energia potrzebna do rozmnażania
    private final int requiredEnergy; // energia pobierana przy rozmnażaniu
    protected MoveTransformation transformation;
    protected List<Vector2D> preferredPositions;
    protected List<Vector2D> notPreferredPositions;

    abstract public Boundary getCurrentBounds();

    @Override
    public void procreate() {
        for(Vector2D position : animals.keySet()) {
            List<Animal> list = animals.get(position);
            if(list.size() > 1) {       // zoptymalizować
               sort(list);
               reverse(list);
                if(list.get(1).getEnergy() >= satisfactoryEnergy) {
                    animals.put(position,list.get(0).generateChild(list.get(1),requiredEnergy,mutation));
                }
            }
        }
    }

    @Override
    public void eatPlants() {
        for(Animal animal : animals.values()) {
            if(plantAt(animal.getPosition()) != null) {
                forbiddenForPlants.remove(animal.getPosition());
                plants.remove(animal.getPosition());
                Animal winningAnimal = max(animals.get(animal.getPosition()));
                winningAnimal.setEnergy(winningAnimal.getEnergy()+plantEnergy);
            }
        }
    }

    @Override
    abstract public void removeDeadAnimals();

    public AbstractWorldMap(int plantEnergy, int satisfactoryEnergy, int requiredEnergy, Mutation mutation) {
        this.plantEnergy = plantEnergy;
        this.satisfactoryEnergy = satisfactoryEnergy;
        this.requiredEnergy = requiredEnergy;
        this.mutation = mutation;
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
        System.out.println(animal.getGenome());
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

    @Override
    public List<Animal> getAnimals() {
        List<Animal> result = new ArrayList<>();
        result.addAll(animals.values());

        return result;
    }
}