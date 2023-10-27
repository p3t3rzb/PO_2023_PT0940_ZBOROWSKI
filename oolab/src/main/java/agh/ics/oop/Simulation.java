package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<MoveDirection> moves;
    private List<Animal> animals = new ArrayList<>();

    public Simulation(List<MoveDirection> moves, List<Vector2D> positions) {
        this.moves = moves;

        for(Vector2D position: positions) {
            animals.add(new Animal(position));
        }
    }

    public void run() {
        for(int i=0; i<moves.size(); i++) {
            Animal temp = animals.get(i % animals.size());
            temp.move(moves.get(i));
            System.out.println("Zwierzę " + i % animals.size() + " : " + temp.toString());
        }
    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
