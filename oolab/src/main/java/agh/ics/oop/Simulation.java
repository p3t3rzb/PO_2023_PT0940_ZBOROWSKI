package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Simulation implements Runnable {
    private final List<MoveDirection> moves;
    private WorldMap map;
    private List<Animal> animals = new ArrayList<>();

    public Simulation(List<MoveDirection> moves, List<Vector2D> positions, WorldMap map) {
        this.moves = moves;
        this.map = map;

        for(Vector2D position: positions) {
            try {
                map.place(new Animal(position));
                animals.add(new Animal(position));
            } catch (PositionAlreadyOccupiedException e) {
                System.out.println("Can't place an animal at position " + position.toString());
            }
        }
    }

    public void run() {
        for(int i=0; i<moves.size(); i++) {
            Animal temp = animals.get(i % animals.size());
            map.move(temp,moves.get(i));
            try {
                Thread.sleep(500);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Animal> getAnimals() {
        return unmodifiableList(animals);
    }
}
