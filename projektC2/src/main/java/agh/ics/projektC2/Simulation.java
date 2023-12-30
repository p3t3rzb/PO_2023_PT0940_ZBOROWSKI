package agh.ics.projektC2;

import agh.ics.projektC2.model.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final int plantCount;
    private final List<Animal> animals = new ArrayList<>();

    public Simulation(List<Vector2D> positions, WorldMap map, int plantCount) {
        this.map = map;
        this.plantCount = plantCount;

        for(Vector2D position: positions) {
            try {
                Animal animal = new Animal(position,100,List.of(0,1,2,3,0,0,0,4,5,6,7,0,0,0));
                map.place(animal);
                animals.add(animal);
            } catch (PositionAlreadyOccupiedException e) {
                System.out.println("Can't place an animal at position " + position.toString());
            }
        }
    }

    public void run() {
        for(int i=0; i<100; i++) {
            this.map.addPlants(plantCount);
            Animal temp = animals.get(i % animals.size());
            map.move(temp);
        }
    }

    public List<Animal> getAnimals() {
        return unmodifiableList(animals);
    }
}
