package agh.ics.oop.model;

import static agh.ics.oop.model.MapDirection.*;
import static agh.ics.oop.model.MoveDirection.*;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2D position;
    //private final Vector2D mapStart = new Vector2D(0,0);
    //private final Vector2D mapEnd = new Vector2D(4,4);

    public Animal() {
        position = new Vector2D(2,2);
        orientation = NORTH;
    }

    public Animal(Vector2D position) {
        this.position = position;
        orientation = NORTH;
    }

    @Override
    public String toString() {
        return switch(orientation) {
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "V";
            case WEST -> "<";
        };
    }

    @Override
    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction, MoveValidator validator) {
        Vector2D temp;

        switch(direction) {
            case RIGHT:
                orientation = orientation.next();
                break;
            case LEFT:
                orientation = orientation.previous();
                break;
            case FORWARD:
                temp = position.add(orientation.toUnitVector());
                if(validator.canMoveTo(temp)) {
                    position = temp;
                }
                break;
            case BACKWARD:
                temp = position.add(orientation.toUnitVector().opposite());
                if(validator.canMoveTo(temp)) {
                    position = temp;
                }
                break;
        }

    }

    public MapDirection getOrientation() {
        return orientation;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }
}
