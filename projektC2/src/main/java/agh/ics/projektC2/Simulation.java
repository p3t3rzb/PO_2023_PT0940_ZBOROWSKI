package agh.ics.projektC2;

import agh.ics.projektC2.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.unmodifiableList;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final int plantCount;
    private static final Random PRNG = new Random();

    public Simulation(List<Vector2D> positions, WorldMap map, int plantCount) {
        this.map = map;
        this.plantCount = plantCount;


        for(Vector2D position: positions) {
            try {
                Animal animal = new Animal(position,100,List.of(PRNG.nextInt(8),PRNG.nextInt(8),PRNG.nextInt(8),PRNG.nextInt(8),PRNG.nextInt(8),PRNG.nextInt(8),PRNG.nextInt(8),PRNG.nextInt(8)));
                map.place(animal);
            } catch (PositionAlreadyOccupiedException e) {
                System.out.println("Can't place an animal at position " + position.toString());
            }
        }
    }

    public void run() {
        for(int i=0; i<200; i++) {
            // 1
            map.removeDeadAnimals();

            // 2
            for(Animal animal : map.getAnimals()) {
                map.move(animal);
            }

            // 3
            map.eatPlants();

            // 4
            map.procreate();

            // 5
            this.map.addPlants(plantCount);
        }
    }
}
