package agh.ics.projektC2.model;

import java.util.List;

public class EarthMap extends AbstractWorldMap {
    private final Vector2D mapStart = new Vector2D(0,0);
    private final Vector2D mapEnd;

    public EarthMap(int width, int height) {
        mapEnd = new Vector2D(width-1,height-1);
        transformation = new EarthTransformation(mapStart,mapEnd);
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(mapStart,mapEnd);
    }

    @Override
    public Animal objectAt(Vector2D position) {
        List<Animal> objectsAt = animals.get(position);
        return objectsAt.isEmpty() ? null : objectsAt.get(0);
    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        // granice mapy obsługuje już EarthTransformation, poza nimi można się poruszać wszędzie
        return true;
    }
}
