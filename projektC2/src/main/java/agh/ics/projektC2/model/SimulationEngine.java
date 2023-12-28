package agh.ics.projektC2.model;

import agh.ics.projektC2.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private final List<Thread> threads = new ArrayList<>();
    private final ExecutorService executorService = newFixedThreadPool(4);

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
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
    }

    public void awaitSimulationsEnd() throws InterruptedException {
        for(Thread thread : threads) {
            thread.join();
        }

        executorService.shutdown();
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
    }

    public void runAsyncInThreadPool() {
        for(Simulation simulation : simulations) {
            Thread thread = new Thread(simulation);
            threads.add(thread);
            executorService.submit(thread);
        }
    }
}
