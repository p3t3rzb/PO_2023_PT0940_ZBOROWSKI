package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap<Animal,Vector2D> {
    protected final Map<Vector2D,Animal> animals = new HashMap<>();

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

        for(Animal animal : animals.values()) {
            result.add(animal);
        }

        return result;
    }
}
