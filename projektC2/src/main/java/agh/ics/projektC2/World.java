package agh.ics.projektC2;

import agh.ics.projektC2.model.*;

import java.util.List;

public class World {
    public static void main(String[] args) {
        Mutation mutation = new SwapMutation();
        FloodingMap map = new FloodingMap(10,10,2,20,10,mutation,0,10);
        map.addObserver(new ConsoleMapDisplay());
        Simulation simulation = new Simulation(map,2,10,100,10);

        simulation.run();
    }
}
