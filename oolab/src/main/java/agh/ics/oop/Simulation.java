package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.WorldMap;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Simulation {
    private final List<MoveDirection> moves;
    private WorldMap<Animal,Vector2D> map;
    private List<Animal> animals = new ArrayList<>();

    public Simulation(List<MoveDirection> moves, List<Vector2D> positions, WorldMap<Animal,Vector2D> map) {
        this.moves = moves;
        this.map = map;

        for(Vector2D position: positions) {
            animals.add(new Animal(position));
            map.place(new Animal(position));
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
