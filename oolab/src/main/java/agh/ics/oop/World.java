package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

import static agh.ics.oop.model.MapDirection.*;
import static agh.ics.oop.model.MoveDirection.*;

public class World {
    public static void run(List<MoveDirection> args) {
        System.out.println("Start");
        for(MoveDirection arg: args) {
            switch(arg) {
                case FORWARD:
                    System.out.println("Zwierzak idzie do przodu");
                    break;
                case BACKWARD:
                    System.out.println("Zwierzak idzie do tyłu");
                    break;
                case RIGHT:
                    System.out.println("Zwierzak skręca w prawo");
                    break;
                case LEFT:
                    System.out.println("Zwierzak skręca w lewo");
                    break;
            }
        }

        System.out.println("Stop");
    }
    public static void main(String[] args) {
        /*System.out.println("System wystartował");
        run(OptionsParser.parse(args));
        System.out.println("System zakończył działanie");

        Vector2D position1 = new Vector2D(1,2);
        System.out.println(position1);
        Vector2D position2 = new Vector2D(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        MapDirection direction1 = EAST;
        System.out.println(direction1.next().toString());
        System.out.println(direction1.previous().toString());
        System.out.println(direction1.toUnitVector().toString());

        System.out.println("");
        Animal testAnimal = new Animal(new Vector2D(1,1));
        testAnimal.move(RIGHT);
        testAnimal.move(FORWARD);
        testAnimal.move(RIGHT);
        testAnimal.move(BACKWARD);
        System.out.println(testAnimal.toString());*/

        try {
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2D> positions = List.of(new Vector2D(2,2), new Vector2D(3,4));
            Simulation simulation = new Simulation(directions, positions, new GrassField(10));
            simulation.run();
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
