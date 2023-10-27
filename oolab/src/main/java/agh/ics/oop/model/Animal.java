package agh.ics.oop.model;

import static agh.ics.oop.model.MapDirection.*;
import static agh.ics.oop.model.MoveDirection.*;

public class Animal {
    private MapDirection orientation;
    private Vector2D position;

    public Animal() {
        position = new Vector2D(2,2);
        orientation = NORTH;
    }

    public Animal(Vector2D position) {
        this.position = position;
        orientation = NORTH;
    }

    public String toString() {
        return position.toString() + ' ' + orientation.toString();
    }

    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction) {
        Vector2D temp = position.add(orientation.toUnitVector());

        switch(direction) {
            case RIGHT:
                orientation = orientation.next();
                break;
            case LEFT:
                orientation = orientation.previous();
                break;
            case FORWARD:
                if(temp.precedes(new Vector2D(4,4)) && temp.follows(new Vector2D(0,0))) {
                    position = temp;
                }
                break;
            case BACKWARD:
                if(temp.precedes(new Vector2D(4,4)) && temp.follows(new Vector2D(0,0))) {
                    position = temp;
                }
                break;
        }

    }
}
