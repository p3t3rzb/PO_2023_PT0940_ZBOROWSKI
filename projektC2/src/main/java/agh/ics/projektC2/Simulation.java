package agh.ics.projektC2;

import agh.ics.projektC2.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final int plantCount;
    private static final Random PRNG = new Random();

    public Simulation(WorldMap map, int plantCount, int animalsCount, int initialEnergy, int genomeLength) {
        this.map = map;
        this.plantCount = plantCount;
        List<Vector2D> positions = new PositionGenerator(map.getCurrentBounds(),map.getForbiddenForAnimals()).getPositions();
        positions = new RandomPositionGenerator(positions,new ArrayList<>(),animalsCount).getPositions();

        for(Vector2D position : positions) {
            try {
                List<Integer> genome = new ArrayList<>();
                for(int i=0; i<genomeLength; i++) {
                    genome.add(PRNG.nextInt(8));
                }
                Animal animal = new Animal(position,initialEnergy,genome);
                map.place(animal);
            } catch (PositionAlreadyOccupiedException e) {
                System.out.println("Can't place an animal at position " + position.toString());
            }
        }
    }

    public void run() {
        for(int i=0; i<300000; i++) {
            // 1
            map.removeDeadAnimals();

            // 2
            map.move();

            // 3
            map.eatPlants();

            // 4
            map.procreate();

            // 5
            this.map.addPlants(plantCount);

            try {
                Thread.sleep(80);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
