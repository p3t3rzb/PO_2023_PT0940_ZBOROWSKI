package agh.ics.projektC2;

import agh.ics.projektC2.model.Animal;
import agh.ics.projektC2.model.MapDirection;
import agh.ics.projektC2.model.MoveValidator;
import agh.ics.projektC2.model.Vector2D;

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

        Animal temp = new Animal(new Vector2D(0,0),100,List.of(1,2,3,4,0,1,1,4));
        for(int i=0; i<8; i++) {
            System.out.println(temp.getOrientation().toString());
            System.out.println(temp.getPosition().toString());
            temp.move(validator);
        }
        System.out.println(temp.getOrientation().toString());
    }
}
