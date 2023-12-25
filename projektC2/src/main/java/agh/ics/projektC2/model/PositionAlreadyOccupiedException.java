package agh.ics.projektC2.model;

public class PositionAlreadyOccupiedException extends Exception {
    public PositionAlreadyOccupiedException(Vector2D position) {
        super("Position " + position.toString() + " is already occupied.");
    }
}
