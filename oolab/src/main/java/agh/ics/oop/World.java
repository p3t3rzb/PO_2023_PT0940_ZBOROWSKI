package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

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
                case OTHER: ;
            }
        }

        System.out.println("Stop");
    }
    public static void main(String[] args) {
        System.out.println("System wystartował");
        OptionsParser temp = new OptionsParser();
        run(temp.Parse(args));
        System.out.println("System zakończył działanie");
    }
}
