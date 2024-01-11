package agh.ics.projektC2.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PositionGenerator {
    private final List<Vector2D> positions = new ArrayList<>();

    public List<Vector2D> getPositions() {
        return positions;
    }

    public PositionGenerator(Boundary boundary, HashMap<Vector2D,Boolean> forbidden) {
        Vector2D startPosition = boundary.bottomLeftCorner();
        Vector2D endPosition = boundary.upperRightCorner();

        for(int i=startPosition.getX(); i<=endPosition.getX(); i++) {
            for(int j=startPosition.getY(); j<= endPosition.getY(); j++) {
                Vector2D position = new Vector2D(i,j);
                if(forbidden.get(position) != Boolean.TRUE) {
                    positions.add(new Vector2D(i,j));
                }
            }
        }
    }
}
