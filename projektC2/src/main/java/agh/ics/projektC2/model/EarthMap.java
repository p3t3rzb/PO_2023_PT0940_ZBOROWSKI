package agh.ics.projektC2.model;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.max;

public class EarthMap extends AbstractWorldMap {
    private final Vector2D mapStart = new Vector2D(0,0);
    private final Vector2D mapEnd;

    public EarthMap(int width, int height, int plantEnergy, int satisfactoryEnergy, int requiredEnergy, Mutation mutation, int minMutationCount, int maxMutationCount) {
        super(plantEnergy,satisfactoryEnergy,requiredEnergy,mutation,minMutationCount,maxMutationCount);
        mapEnd = new Vector2D(width-1,height-1);
        transformation = new EarthTransformation(mapStart,mapEnd);
        growth = new EquatorGrowth(mapStart, mapEnd, forbiddenForPlants);
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(mapStart,mapEnd);
    }

    @Override
    public WorldElement objectAt(Vector2D position) {
        List<Animal> objectsAt = animals.get(position);
        return objectsAt.isEmpty() ? plantAt(position) : max(objectsAt);
    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        // granice mapy obsługuje już EarthTransformation, poza nimi można się poruszać wszędzie
        return true;
    }
}
