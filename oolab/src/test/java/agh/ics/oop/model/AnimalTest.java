package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import org.w3c.dom.css.Rect;

import static agh.ics.oop.model.MoveDirection.*;
import static agh.ics.oop.model.MoveDirection.FORWARD;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {
    RectangularMap map = new RectangularMap(3,3);
    @Test
    public void orientationTest() {
        Animal temp = new Animal();
        temp.move(RIGHT,map);
        assertEquals(temp.toString(), ">");
        assertEquals(temp.getPosition().toString(),"(2,2)");
        temp.move(LEFT,map);
        temp.move(LEFT,map);
        assertEquals(temp.toString(), "<");
        assertEquals(temp.getPosition().toString(),"(2,2)");
    }

    @Test
    public void positionTest() {
        Animal temp = new Animal();
        temp.move(RIGHT,map);
        temp.move(FORWARD,map);
        temp.move(FORWARD,map);
        assertEquals(temp.toString(), ">");
        assertEquals(temp.getPosition().toString(), "(2,2)");
        temp.move(RIGHT,map);
        temp.move(FORWARD,map);
        assertEquals(temp.toString(), "V");
        assertEquals(temp.getPosition().toString(), "(2,1)");
    }

    @Test
    public void mapBoundariesTest() {
        Animal temp = new Animal();
        temp.move(FORWARD,map);
        temp.move(FORWARD,map);
        temp.move(FORWARD,map);
        assertEquals(temp.toString(), "^");
        assertEquals(temp.getPosition().toString(), "(2,2)");
        temp.move(LEFT,map);
        temp.move(FORWARD,map);
        temp.move(FORWARD,map);
        temp.move(FORWARD,map);
        assertEquals(temp.toString(), "<");
        assertEquals(temp.getPosition().toString(), "(0,2map)");
    }
}
