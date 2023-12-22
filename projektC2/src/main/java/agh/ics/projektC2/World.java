package agh.ics.projektC2;

import agh.ics.projektC2.model.MapDirection;

public class World {
    public static void main(String[] args) {
        MapDirection test = MapDirection.NORTH;
        System.out.println(test.toString());
        System.out.println(test.next(0).toString());
        System.out.println(test.next(7).toString());
        System.out.println(test.previous(7).toString());
    }
}
