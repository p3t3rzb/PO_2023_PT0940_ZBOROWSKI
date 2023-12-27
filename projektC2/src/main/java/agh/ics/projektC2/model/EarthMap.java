package agh.ics.projektC2.model;

public class EarthMap extends AbstractWorldMap {
    // dodać rośliny
    private final Vector2D mapStart = new Vector2D(0,0);
    private final Vector2D mapEnd;

    public EarthMap(int width, int height) {
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
}
