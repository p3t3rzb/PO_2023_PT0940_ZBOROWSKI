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

    public int directionToInt() {
        return ordinal();
    }

    public static MoveDirection intToDirection(int number) {
        if(number >= 0 && number < 8) {
            return values()[number];
        }

        return FORWARD;
    }
}
