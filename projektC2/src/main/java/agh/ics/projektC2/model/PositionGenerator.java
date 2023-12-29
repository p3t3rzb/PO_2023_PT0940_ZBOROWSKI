package agh.ics.projektC2.model;

import java.util.ArrayList;
import java.util.List;

public class PositionGenerator {
    private final List<Vector2D> positions = new ArrayList<>();

    public List<Vector2D> getPositions() {
        return positions;
    }

    public PositionGenerator(Boundary boundary) {
        Vector2D startPosition = boundary.bottomLeftCorner();
        Vector2D endPosition = boundary.upperRightCorner();

        for(int i=startPosition.getX(); i<=endPosition.getX(); i++) {
            for(int j=startPosition.getY(); j<= endPosition.getY(); j++) {
                positions.add(new Vector2D(i,j));
            }
        }
    }
}
