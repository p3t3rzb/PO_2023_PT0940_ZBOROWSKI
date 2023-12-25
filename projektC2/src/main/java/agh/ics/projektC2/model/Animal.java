package agh.ics.projektC2.model;


import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2D position;
    private final List<Integer> genome;
    private int energy;
    private int currentGene;
    private int age;
    private static final Random PRNG = new Random();

    public Animal(Vector2D position, int energy, List<Integer> genome) {
        this.position = position;
        this.energy = energy;
        this.genome = genome;
        age = 0;
        currentGene = PRNG.nextInt(max(1,genome.size()));
        orientation = MapDirection.randomDirection();
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

    public void move(MoveValidator validator) {
        MoveDirection direction = MoveDirection.intToDirection(genome.get(currentGene).intValue());

        currentGene = currentGene+1;
        if(currentGene == genome.size()) {
            currentGene = 0;
        }

        Vector2D temp;
        orientation = orientation.rotate(direction);
        temp = position.add(orientation.toUnitVector());

        if(validator.canMoveTo(temp)) {
            position = temp;
        }

        age++;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }
}
