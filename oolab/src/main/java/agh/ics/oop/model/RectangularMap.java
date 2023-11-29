package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;


public class RectangularMap extends AbstractWorldMap {
    private final Vector2D mapStart = new Vector2D(0,0);
    private final Vector2D mapEnd;
    //private final MapVisualizer visualizer = new MapVisualizer(this);

    public RectangularMap(int width, int height) {
        mapEnd = new Vector2D(width-1,height-1);
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(mapStart,mapEnd);
    }

    @Override
    public Animal objectAt(Vector2D position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        return position.follows(mapStart) && position.precedes(mapEnd) && isOccupied(position) == false;
    }

    /*public String toString() {
        return visualizer.draw(mapStart,mapEnd);
    }*/
}
