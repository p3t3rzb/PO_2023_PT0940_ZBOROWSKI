package agh.ics.projektC2;

import agh.ics.projektC2.model.MapDirection;
import static agh.ics.projektC2.model.MoveDirection.*;

public class World {
    public static void main(String[] args) {
        MapDirection test = MapDirection.NORTH;
        System.out.println(test.toString());
        System.out.println(test.rotate(FORWARD).toString());
        System.out.println(test.rotate(FORWARD_LEFT).toString());
        System.out.println(test.rotate(FORWARD_RIGHT).toString());
    }
}
