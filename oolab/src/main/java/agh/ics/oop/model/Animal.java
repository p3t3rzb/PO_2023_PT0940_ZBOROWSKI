package agh.ics.oop.model;

import static agh.ics.oop.model.MapDirection.*;
import static agh.ics.oop.model.MoveDirection.*;

public class Animal {
    private MapDirection orientation;
    private Vector2D position;
    private final Vector2D mapStart = new Vector2D(0,0);
    private final Vector2D mapEnd = new Vector2D(4,4);

    public Animal() {
        position = new Vector2D(2,2);
        orientation = NORTH;
    }

    public Animal(Vector2D position) {
        this.position = position;
        orientation = NORTH;
    }

    private boolean isOnTheMap(Vector2D position) {
        if(position.precedes(mapEnd) && position.follows(mapStart)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return position.toString() + ' ' + orientation.toString();
    }

    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction) {
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
                if(isOnTheMap(temp)) {
                    position = temp;
                }
                break;
            case BACKWARD:
                temp = position.add(orientation.toUnitVector().opposite());
                if(isOnTheMap(temp)) {
                    position = temp;
                }
                break;
        }

    }

    public Vector2D getPosition() {
        return position;
    }
}
