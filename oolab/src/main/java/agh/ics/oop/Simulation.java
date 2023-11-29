package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Simulation {
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
                e.printStackTrace();
            }
        }
    }

    public void run() {
        for(int i=0; i<moves.size(); i++) {
            Animal temp = animals.get(i % animals.size());
            map.move(temp,moves.get(i));
            System.out.println("Zwierzę " + i % animals.size() + " : " + temp.toString());
            System.out.println(map.toString());
        }
    }

    public List<Animal> getAnimals() {
        return unmodifiableList(animals);
    }
}
