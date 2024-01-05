package agh.ics.projektC2;

import agh.ics.projektC2.model.*;

import java.util.List;

public class World {
    public static void main(String[] args) {
        Mutation mutation = new FullyRandomMutation();
        EarthMap map = new EarthMap(10,10,2,20,10,mutation,0,10);
        map.addObserver(new ConsoleMapDisplay());
        List<Vector2D> positions = List.of(new Vector2D(0,0),new Vector2D(2,2), new Vector2D(5,5), new Vector2D(4,4), new Vector2D(7,5), new Vector2D(8,9));
        Simulation simulation = new Simulation(map,2,10,100,10);

        simulation.run();
    }
}
