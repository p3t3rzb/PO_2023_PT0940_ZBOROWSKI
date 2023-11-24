package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

import static java.lang.Math.*;

public class GrassField extends AbstractWorldMap {
    private Vector2D mapStart, mapEnd;
    private final Map<Vector2D,Grass> grasses = new HashMap<>();

    public GrassField(int n) {
        mapStart = new Vector2D(Integer.MAX_VALUE,Integer.MAX_VALUE);
        mapEnd = new Vector2D(Integer.MIN_VALUE,Integer.MIN_VALUE);
        int maxMap = (int)sqrt(n*10);

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(maxMap,maxMap,n);
        Iterator<Vector2D> positionsIterator = randomPositionGenerator.iterator();

        while(positionsIterator.hasNext()) {
            Vector2D grassPosition = positionsIterator.next();
            grasses.put(grassPosition, new Grass(grassPosition));
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

    private void mapCheck(Vector2D position) {
        mapStart = mapStart.lowerLeft(position);
        mapEnd = mapEnd.upperRight(position);
    }

    private void updateMapSize() {
        mapStart = new Vector2D(Integer.MAX_VALUE,Integer.MAX_VALUE);
        mapEnd = new Vector2D(Integer.MIN_VALUE,Integer.MIN_VALUE);
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

    public String toString() {
        updateMapSize();
        return new MapVisualizer(this).draw(mapStart,mapEnd);
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
