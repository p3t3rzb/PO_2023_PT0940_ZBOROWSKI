package agh.ics.projektC2.model;


import java.util.Collections;
import java.util.List;
import static agh.ics.projektC2.model.MapDirection.*;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2D position;
    private int energy;
    //private int genomeLength;
    private final List<Integer> genome;

    public Animal(Vector2D position, int energy, List<Integer> genome) {
        this.position = position;
        this.energy = energy;
        this.genome = genome;
        orientation = NORTH;
    }

    public List<Integer> getGenome() {
        return Collections.unmodifiableList(genome); // sprawdzić później
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        return switch(orientation) {
            case NORTH, NORTH_EAST -> "^";
            case EAST, SOUTH_EAST -> ">";
            case SOUTH, SOUTH_WEST -> "V";
            case WEST, NORTH_WEST -> "<";
        };
    }

    @Override
    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction, MoveValidator validator) {
        Vector2D temp;
        orientation = orientation.rotate(direction);
        temp = position.add(orientation.toUnitVector());

        if(validator.canMoveTo(temp)) {
            position = temp;
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
