package agh.ics.oop;

import agh.ics.oop.model.*;
import org.w3c.dom.css.Rect;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import static agh.ics.oop.model.MapDirection.*;
import static agh.ics.oop.model.MoveDirection.*;

public class World {
    public static void main(String[] args) {
        try {
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2D> positions = List.of(new Vector2D(2,2), new Vector2D(3,4));
            List<Simulation> simulations = new ArrayList<>();
            ConsoleMapDisplay observer = new ConsoleMapDisplay();
            for(int i=0; i<50; i++) {
                GrassField temp = new GrassField(10);
                RectangularMap temp2 = new RectangularMap(5,5);
                temp.addObserver(observer);
                temp2.addObserver(observer);
                simulations.add(new Simulation(directions, positions, temp));
                simulations.add(new Simulation(directions, positions, temp2));
            }
            SimulationEngine engine = new SimulationEngine(simulations);
            engine.runAsync();
            engine.awaitSimulationsEnd();
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("System zakończył działanie");
    }
}
