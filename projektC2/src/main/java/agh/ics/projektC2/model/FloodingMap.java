package agh.ics.projektC2.model;

import java.util.HashMap;
import java.util.List;

import static java.util.Collections.max;

public class FloodingMap extends AbstractWorldMap {
    private final Vector2D mapStart = new Vector2D(0,0);
    private final Vector2D mapEnd;
    private final HashMap<Vector2D,Water> waters = new HashMap<>();

    public FloodingMap(int width, int height, int plantEnergy, int satisfactoryEnergy, int requiredEnergy, Mutation mutation, int minMutationCount, int maxMutationCount) {
        super(plantEnergy,satisfactoryEnergy,requiredEnergy,mutation,minMutationCount,maxMutationCount);
        mapEnd = new Vector2D(width-1,height-1);
        transformation = new IdentityTransformation();
        growth = new EquatorGrowth(mapStart, mapEnd, forbiddenForPlants);
    }

    public Water waterAt(Vector2D position) {
        return waters.get(position);
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(mapStart,mapEnd);
    }

    @Override
    public WorldElement objectAt(Vector2D position) {
        Water water = waterAt(position);
        if(water != null) {
            return water;
        } else {
            List<Animal> objectsAt = animals.get(position);
            return objectsAt.isEmpty() ? plantAt(position) : max(objectsAt);
        }
    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        return position.follows(mapStart) && position.precedes(mapEnd) && waterAt(position) == null;
    }
}
