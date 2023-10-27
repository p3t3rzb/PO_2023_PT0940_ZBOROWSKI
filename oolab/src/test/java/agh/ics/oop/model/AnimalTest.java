package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static agh.ics.oop.model.MoveDirection.*;
import static agh.ics.oop.model.MoveDirection.FORWARD;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {
    @Test
    public void orientationTest() {
        Animal temp = new Animal();
        temp.move(RIGHT);
        assertEquals(temp.toString(), "(2,2) Wschód");
        temp.move(LEFT);
        temp.move(LEFT);
        assertEquals(temp.toString(), "(2,2) Zachód");
    }

    @Test
    public void positionTest() {
        Animal temp = new Animal();
        temp.move(RIGHT);
        temp.move(FORWARD);
        temp.move(FORWARD);
        assertEquals(temp.toString(), "(4,2) Wschód");
        temp.move(RIGHT);
        temp.move(FORWARD);
        assertEquals(temp.toString(), "(4,1) Południe");
    }

    @Test
    public void mapBoundariesTest() {
        Animal temp = new Animal();
        temp.move(FORWARD);
        temp.move(FORWARD);
        temp.move(FORWARD);
        assertEquals(temp.toString(), "(2,4) Północ");
        temp.move(LEFT);
        temp.move(FORWARD);
        temp.move(FORWARD);
        temp.move(FORWARD);
        assertEquals(temp.toString(), "(0,4) Zachód");
    }
}
