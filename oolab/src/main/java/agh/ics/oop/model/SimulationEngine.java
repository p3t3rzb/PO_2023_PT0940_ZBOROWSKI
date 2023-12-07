package agh.ics.oop.model;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private final List<Thread> threads = new ArrayList<>();

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runSync() {
        for(Simulation simulation : simulations) {
            simulation.run();
        }
    }

    public void runAsync() {
        for(Simulation simulation : simulations) {
            Thread temp = new Thread(simulation);
            threads.add(temp);
            temp.start();
        }
    }

    public void awaitSimulationsEnd() throws InterruptedException {
        for(Thread thread : threads) {
            thread.join();
        }
    }
}
