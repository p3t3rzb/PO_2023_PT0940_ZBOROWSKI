package agh.ics.projektC2.model;

public enum MoveDirection {
    FORWARD,
    FORWARD_RIGHT,
    RIGHT,
    BACKWARD_RIGHT,
    BACKWARD,
    BACKWARD_LEFT,
    LEFT,
    FORWARD_LEFT;

    public int toInt() {
        return ordinal();
    }
}
