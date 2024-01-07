package agh.ics.projektC2.model;

import javax.swing.text.Position;
import java.util.*;

import static java.util.Arrays.copyOfRange;
import static java.util.Collections.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected final AnimalMultiMap animals = new AnimalMultiMap();
    protected final HashMap<Vector2D,Plant> plants = new HashMap<>();
    protected final HashMap<Vector2D,Boolean> forbiddenForPlants = new HashMap<>();
    protected HashMap<Vector2D,Boolean> forbiddenForAnimals = new HashMap<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);
    private final List<MapChangeListener> observers = new ArrayList<>();
    protected final int mapID;
    protected final int plantEnergy;
    protected Mutation mutation;
    private final int minMutationCount;
    private final int maxMutationCount;
    private static int objectsCount = 0;
    private final int satisfactoryEnergy; // energia potrzebna do rozmnażania
    private final int requiredEnergy; // energia pobierana przy rozmnażaniu
    protected MoveTransformation transformation;
    private static final Random PRNG = new Random();
    protected PlantGrowth growth;

    abstract public Boundary getCurrentBounds();




    public AbstractWorldMap(int plantEnergy, int satisfactoryEnergy, int requiredEnergy, Mutation mutation, int minMutationCount, int maxMutationCount) {
        this.plantEnergy = plantEnergy;
        this.satisfactoryEnergy = satisfactoryEnergy;
        this.requiredEnergy = requiredEnergy;
        this.mutation = mutation;
        this.minMutationCount = minMutationCount;
        this.maxMutationCount = maxMutationCount;
        mapID = objectsCount;
        objectsCount++;
    }

    public int getID() {
        return mapID;
    }

    @Override
    public HashMap<Vector2D, Boolean> getForbiddenForAnimals() {
        return forbiddenForAnimals;
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
    public void procreate() {
        for(Vector2D position : animals.keySet()) {
            List<Animal> list = animals.get(position);
            if(list.size() > 1) {
                sort(list);
                reverse(list);

                // obsługa przypadków gdy rywalizuje kilka o tych samych "wycenach" na pierwszym lub drugim miejscu

                int i,j;
                for(i=0; i < list.size() && list.get(i) == list.get(0); i++);
                for(j=i; j < list.size() && list.get(j) == list.get(i); j++);

                List<Animal> firstValue = new ArrayList<>();
                List<Animal> secondValue = new ArrayList<>();

                for(int t = 0; t < i; t++) {
                    firstValue.add(list.get(t));
                }
                for(int t = i; t < j; t++) {
                    secondValue.add(list.get(t));
                }

                Collections.shuffle(firstValue);
                Collections.shuffle(secondValue);

                for(int t = 0; t < i; t++) {
                    list.set(t,firstValue.get(t));
                }
                for(int t = i; t < j; t++) {
                    list.set(t,secondValue.get(t-i));
                }

                if(list.get(1).getEnergy() >= satisfactoryEnergy) {
                    animals.put(position,list.get(0).generateChild(list.get(1),requiredEnergy,mutation,minMutationCount,maxMutationCount));
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
                List<Animal> candidates = animals.get(animal.getPosition());
                sort(candidates);
                reverse(candidates);
                int i; // ilość zwierząt o tej samej wycenie
                for(i=0; i < candidates.size() && candidates.get(i) == candidates.get(0); i++);
                Animal winningAnimal = candidates.get(PRNG.nextInt(i));
                winningAnimal.setEnergy(winningAnimal.getEnergy()+plantEnergy);
            }
        }
    }

    @Override
    public void removeDeadAnimals() {
        for(Animal animal : animals.values()) {
            if(animal.getEnergy() == 0) {
                animals.remove(animal.getPosition(),animal);
            }
        }
    }

    @Override
    public void addPlants(int count) {
        List<Vector2D> positions = growth.positions(count);
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
    abstract public void move();

    protected void moveAnimal(Animal animal) {
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