package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2D;

public class World {
    public static void run(MoveDirection[] args) {
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
        System.out.println("System wystartował");
        run(OptionsParser.parse(args));
        System.out.println("System zakończył działanie");

        Vector2D position1 = new Vector2D(1,2);
        System.out.println(position1);
        Vector2D position2 = new Vector2D(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
    }
}
