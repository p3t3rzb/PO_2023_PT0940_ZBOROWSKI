package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

import static java.lang.Math.*;

public class GrassField extends AbstractWorldMap {
    private static final Vector2D BASEVECTOR = new Vector2D(0,0);
    private Vector2D mapStart, mapEnd;
    private final Map<Vector2D,Grass> grasses = new HashMap<>();

    public GrassField(int n) {
        mapStart = BASEVECTOR;
        mapEnd = BASEVECTOR;
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

    private void mapCheck(Vector2D position) {
        mapStart = new Vector2D(min(position.getX(),mapStart.getX()),min(position.getY(),mapStart.getY()));
        mapEnd = new Vector2D(max(position.getX(),mapEnd.getX()),max(position.getY(),mapEnd.getY()));
    }

    private void updateMapSize() {
        mapStart = BASEVECTOR;
        mapEnd = BASEVECTOR;
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
