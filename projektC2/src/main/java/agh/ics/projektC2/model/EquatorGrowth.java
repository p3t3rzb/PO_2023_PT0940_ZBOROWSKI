package agh.ics.projektC2.model;

import java.util.List;
import java.util.Set;

public class EquatorGrowth implements PlantGrowth {
    private final Set<Vector2D> forbiddenForPlants;
    private final Boundary preferredBoundary;
    private final Boundary notPreferredBoundaryLower;
    private final Boundary notPreferredBoundaryUpper;

    public EquatorGrowth(Vector2D mapStart, Vector2D mapEnd, Set<Vector2D> forbiddenForPlants) {
        this.forbiddenForPlants = forbiddenForPlants;

        preferredBoundary = new Boundary(new Vector2D(mapStart.getX(),mapStart.getY() + (int)(0.4 * (mapEnd.getY() - mapStart.getY())+1)),
                new Vector2D(mapEnd.getX(),mapStart.getY() + (int)(0.6 * (mapEnd.getY() - mapStart.getY()))));
        notPreferredBoundaryLower = new Boundary(new Vector2D(mapStart.getX(),mapStart.getY()),
                new Vector2D(mapEnd.getY(),mapStart.getY() + (int)(0.4 * (mapEnd.getY() - mapStart.getY()))));
        notPreferredBoundaryUpper = new Boundary(new Vector2D(mapStart.getX(),mapStart.getY() + (int)(0.6 * (mapEnd.getY() - mapStart.getY())-1)),
                new Vector2D(mapEnd.getX(),mapEnd.getY()));
    }

    @Override
    public List<Vector2D> positions(int count) {
        List<Vector2D> preferredPositions = new PositionGenerator(preferredBoundary,forbiddenForPlants).getPositions();
        List<Vector2D> notPreferredPositions = new PositionGenerator(notPreferredBoundaryUpper,forbiddenForPlants).getPositions();
        notPreferredPositions.addAll(new PositionGenerator(notPreferredBoundaryLower,forbiddenForPlants).getPositions());

        return new RandomPositionGenerator(preferredPositions,notPreferredPositions,count).getPositions();
    }

    @Override
    public Boundary getPreferredBoundary() {
        return preferredBoundary;
    }
}
