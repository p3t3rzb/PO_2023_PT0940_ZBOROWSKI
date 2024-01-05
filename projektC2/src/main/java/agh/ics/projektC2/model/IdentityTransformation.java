package agh.ics.projektC2.model;

public class IdentityTransformation implements MoveTransformation {
    public OrientedPosition transform(OrientedPosition currentPosition, MapDirection lastMove) {
        return currentPosition;
    }
}
