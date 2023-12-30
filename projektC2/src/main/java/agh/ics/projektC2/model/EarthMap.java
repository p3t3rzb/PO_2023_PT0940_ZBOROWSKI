package agh.ics.projektC2.model;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.max;

public class EarthMap extends AbstractWorldMap {
    private final Vector2D mapStart = new Vector2D(0,0);
    private final Vector2D mapEnd;
    private final Boundary preferredBoundary;
    private final Boundary notPreferredBoundaryUpper;
    private final Boundary notPreferredBoundaryLower;

    public EarthMap(int width, int height) {
        mapEnd = new Vector2D(width-1,height-1);
        transformation = new EarthTransformation(mapStart,mapEnd);

        preferredBoundary = new Boundary(new Vector2D(mapStart.getX(),mapStart.getY() + (int)(0.4 * (mapEnd.getY() - mapStart.getY()))),
                                         new Vector2D(mapEnd.getX(),mapStart.getY() + (int)(0.6 * (mapEnd.getY() - mapStart.getY()))));
        notPreferredBoundaryLower = new Boundary(new Vector2D(mapStart.getX(),mapStart.getY()),
                                                 new Vector2D(mapEnd.getY(),mapStart.getY() + (int)(0.4 * (mapEnd.getY() - mapStart.getY())) - 1));
        notPreferredBoundaryUpper = new Boundary(new Vector2D(mapStart.getX(),mapStart.getY() + (int)(0.6 * (mapEnd.getY() - mapStart.getY())) + 1),
                                                 new Vector2D(mapEnd.getX(),mapEnd.getY()));
    }

    @Override
    public void addPlants(int count) {
        preferredPositions = new PositionGenerator(preferredBoundary,forbiddenForPlants).getPositions();
        notPreferredPositions = new PositionGenerator(notPreferredBoundaryUpper,forbiddenForPlants).getPositions();
        notPreferredPositions.addAll(new PositionGenerator(notPreferredBoundaryLower,forbiddenForPlants).getPositions());

        super.addPlants(count);
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
