package agh.ics.projektC2.model;

import static agh.ics.projektC2.model.MoveDirection.BACKWARD;

public class EarthTransformation implements MoveTransformation {
    private final Vector2D mapStart, mapEnd;

    public EarthTransformation(Vector2D mapStart, Vector2D mapEnd) {
        this.mapStart = mapStart;
        this.mapEnd = mapEnd;
    }

    public OrientedPosition transform(OrientedPosition currentPosition, MapDirection lastMove) {
        Vector2D position = currentPosition.position();
        MapDirection orientation = currentPosition.orientation();
        int width = mapEnd.getX()-mapStart.getX()+1;

        // odbijanie od góry

        if(position.getY() < mapStart.getY() || position.getY() > mapEnd.getY()) {
            orientation = orientation.rotate(BACKWARD);
            position = position.add(orientation.toUnitVector());
        }

        // boki

        int x = mapStart.getX() + (width+position.getX()-mapStart.getX())%width;
        position = new Vector2D(x,position.getY());

        return new OrientedPosition(position,orientation);
    }
}
