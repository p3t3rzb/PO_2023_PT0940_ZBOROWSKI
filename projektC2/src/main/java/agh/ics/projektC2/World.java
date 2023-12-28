package agh.ics.projektC2;

import agh.ics.projektC2.model.*;

import java.util.ArrayList;
import java.util.List;

import static agh.ics.projektC2.model.MoveDirection.*;

public class World {
    public static void main(String[] args) {
        /*MapDirection test = MapDirection.NORTH;
        System.out.println(test.toString());
        System.out.println(test.rotate(FORWARD).toString());
        System.out.println(test.rotate(FORWARD_LEFT).toString());
        System.out.println(test.rotate(FORWARD_RIGHT).toString());*/

        MoveValidator validator = new MoveValidator() {
            @Override
            public boolean canMoveTo(Vector2D position) {
                return true;
            }
        };

        EarthMap map = new EarthMap(10,10);
        map.addObserver(new ConsoleMapDisplay());
        List<Vector2D> positions = List.of(new Vector2D(0,0),new Vector2D(2,2));
        Simulation simulation = new Simulation(positions,map);

        simulation.run();

        /*Animal temp = new Animal(new Vector2D(0,0),100,List.of(0,0,0,0,0,0,0,0,0,0));
        for(int i=0; i<12; i++) {
            System.out.println(temp.getOrientation().toString());
            System.out.println(temp.getPosition().toString());
            temp.move(validator,transformation);
        }
        System.out.println(temp.getOrientation().toString());*/
    }
}
