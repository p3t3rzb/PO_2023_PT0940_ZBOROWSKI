package agh.ics.oop.model;

import java.util.*;

import static java.lang.Math.*;

public class GrassField extends AbstractWorldMap {
    private Vector2D mapVisibleLowerLeft, mapVisibleUpperRight;
    private final Map<Vector2D,Grass> grasses = new HashMap<>();

    public GrassField(int n) {
        mapVisibleLowerLeft = new Vector2D(Integer.MAX_VALUE,Integer.MAX_VALUE);
        mapVisibleUpperRight = new Vector2D(Integer.MIN_VALUE,Integer.MIN_VALUE);
        int maxMap = (int)sqrt(n*10);

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(maxMap,maxMap,n);
        for(Vector2D grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }

    }

    @Override
    public Boundary getCurrentBounds() {
        updateMapSize();
        return new Boundary(mapVisibleLowerLeft,mapVisibleUpperRight);
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

    private void mapCheck(Vector2D position) {
        mapVisibleLowerLeft = mapVisibleLowerLeft.lowerLeft(position);
        mapVisibleUpperRight = mapVisibleUpperRight.upperRight(position);
    }

    private void updateMapSize() {
        mapVisibleLowerLeft = new Vector2D(Integer.MAX_VALUE,Integer.MAX_VALUE);
        mapVisibleUpperRight = new Vector2D(Integer.MIN_VALUE,Integer.MIN_VALUE);
        for(Vector2D position : animals.keySet()) {
            mapCheck(position);
        }
        for(Vector2D position : grasses.keySet()) {
            mapCheck(position);
        }
    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        if(animals.get(position) == null) {
            return true;
        }
        return false;
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> result = super.getElements();

        for(Grass grass : grasses.values()) {
            result.add(grass);
        }

        return result;
    }
}
