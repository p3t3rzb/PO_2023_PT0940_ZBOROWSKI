package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;


public class RectangularMap extends AbstractWorldMap {
    private final Vector2D mapStart = new Vector2D(0,0);
    private final Vector2D mapEnd;

    public RectangularMap(int width, int height) {
        mapEnd = new Vector2D(width-1,height-1);
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
