package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static agh.ics.oop.model.MoveDirection.FORWARD;
import static agh.ics.oop.model.MoveDirection.RIGHT;
import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {
    private WorldMap<Animal,Vector2D> map = new GrassField(5);
    private static final Vector2D SAMPLEVECTOR = new Vector2D(2,2);

    @Test
    public void placeTest() {
        assertTrue(map.place(new Animal(SAMPLEVECTOR)));
        assertFalse(map.place(new Animal(SAMPLEVECTOR)));
        assertTrue(map.place(new Animal(new Vector2D(1000000,5))));
    }


    @Test
    public void isOccupiedTest() {
        Animal temp = new Animal(SAMPLEVECTOR);
        assertFalse(map.isOccupied(SAMPLEVECTOR));
        map.place(temp);
        assertTrue(map.isOccupied(SAMPLEVECTOR));
        map.move(temp,FORWARD);
        assertFalse(map.isOccupied(SAMPLEVECTOR));
    }

    @Test
    public void canMoveToTest() {
        assertTrue(map.canMoveTo(SAMPLEVECTOR));
        Animal temp = new Animal(SAMPLEVECTOR);
        map.place(temp);
        assertFalse(map.canMoveTo(SAMPLEVECTOR));
        assertTrue(map.canMoveTo(new Vector2D(5,5)));
    }

    @Test
    public void objectAtTest() {
        assertEquals(map.objectAt(SAMPLEVECTOR),null);
        Animal temp = new Animal(SAMPLEVECTOR);
        map.place(temp);
        assertEquals(map.objectAt(SAMPLEVECTOR),temp);
        map.move(temp,RIGHT);
        assertEquals(map.objectAt(SAMPLEVECTOR),temp);
        map.move(temp,FORWARD);
        assertNotEquals(map.objectAt(SAMPLEVECTOR),temp);
    }
}
