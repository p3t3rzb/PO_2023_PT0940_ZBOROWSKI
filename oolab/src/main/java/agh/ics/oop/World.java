package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        Application.launch(SimulationApp.class, args);



        try {
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2D> positions = List.of(new Vector2D(2,2), new Vector2D(3,4));
            List<Simulation> simulations = new ArrayList<>();
            ConsoleMapDisplay observer = new ConsoleMapDisplay();
            for(int i=0; i<50; i++) {
                GrassField grassMap = new GrassField(10);
                RectangularMap rectangularMap = new RectangularMap(5,5);
                grassMap.addObserver(observer);
                rectangularMap.addObserver(observer);
                simulations.add(new Simulation(directions, positions, grassMap));
                simulations.add(new Simulation(directions, positions, rectangularMap));
            }
            SimulationEngine engine = new SimulationEngine(simulations);
            engine.runAsyncInThreadPool();
            engine.awaitSimulationsEnd();
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("System zakończył działanie");
    }
}
