package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class RectangularMap implements WorldMap<Animal,Vector2D> {
    private Map<Vector2D,Animal> animals = new HashMap<>();
    private final int width, height;
    private final Vector2D mapStart = new Vector2D(0,0);
    private final Vector2D mapEnd;

    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;
        mapEnd = new Vector2D(width-1,height-1);
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
    public void move(Animal animal, MoveDirection direction) {
        animals.remove(animal.getPosition());
        animal.move(direction,this);
        animals.put(animal.getPosition(),animal);
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return (animals.get(position) != null);
    }

    @Override
    public Animal objectAt(Vector2D position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        return position.follows(mapStart) && position.precedes(mapEnd) && isOccupied(position) == false;
    }

    public String toString() {
        return new MapVisualizer(this).draw(mapStart,mapEnd);
    }
}
