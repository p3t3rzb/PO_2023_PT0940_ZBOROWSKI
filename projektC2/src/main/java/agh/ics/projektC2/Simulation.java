package agh.ics.projektC2;

import agh.ics.projektC2.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final int plantCount, waitingTime;
    private boolean onPause = false;
    private boolean running = true;
    private static final Random PRNG = new Random();

    public Simulation(WorldMap map, int plantCount, int animalsCount, int initialEnergy, int genomeLength, int waitingTime) {
        this.map = map;
        this.plantCount = plantCount;
        this.waitingTime = waitingTime;
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

    public boolean isOnPause() {
        return onPause;
    }

    public synchronized void pause() {
        onPause = true;
    }

    public synchronized void resume() {
        onPause = false;
        notify();
    }

    public void end() {
        running = false;
    }

    public void run() {
        while(running) {
            while (onPause) {
                synchronized(this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Thread Interrupted");
                    }
                }
            }

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
                Thread.sleep(waitingTime);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
