package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class GrassField implements WorldMap<Animal,Vector2D> {
    private final int nGrass;
    private Vector2D mapStart, mapEnd;
    private final Map<Vector2D,Animal> animals = new HashMap<>();
    private final Map<Vector2D,Grass> grasses = new HashMap<>();

    public GrassField(int n) {
        this.nGrass = n;
        mapStart = new Vector2D(0,0);
        mapEnd = mapStart;
        int count = 0;
        int maxMap = (int)sqrt(n*10);

        while(count < n) {
            Random rn = new Random();
            Vector2D randomPosition = new Vector2D(rn.nextInt()%maxMap,rn.nextInt()%maxMap);
            if(grasses.containsKey(randomPosition) == false) {
                grasses.put(randomPosition,new Grass(randomPosition));
                count++;
            }
        }
    }
    @Override
    public WorldElement objectAt(Vector2D position) {
        WorldElement result = animals.get(position);
        if(result != null) {
            return result;
        } else {
            result = grasses.get(position);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return (objectAt(position) != null);
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

    private void mapCheck(Vector2D position) {
        if(position.precedes(mapStart)) {
            mapStart = position;
        }
        if(position.follows(mapEnd)) {
            mapEnd = position;
        }
    }

    private void updateMapSize() {
        for(Vector2D position : animals.keySet()) {
            mapCheck(position);
        }
        for(Vector2D position : grasses.keySet()) {
            mapCheck(position);
        }
    }

    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition()) == false) {
            return false;
        }

        animals.put(animal.getPosition(),animal);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        if(animals.get(position) == null) {
            return true;
        }
        return false;
    }

    public String toString() {
        updateMapSize();
        return new MapVisualizer(this).draw(mapStart,mapEnd);
    }
}
