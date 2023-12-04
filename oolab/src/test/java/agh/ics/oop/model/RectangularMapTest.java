package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static agh.ics.oop.model.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    private WorldMap map = new RectangularMap(5,5);
    private static final Vector2D SAMPLEVECTOR = new Vector2D(2,2);

    @Test
    public void placeTest() {
        assertDoesNotThrow(() -> map.place(new Animal(SAMPLEVECTOR)));
        assertThrows(PositionAlreadyOccupiedException.class, () -> map.place(new Animal(SAMPLEVECTOR)));
        assertThrows(PositionAlreadyOccupiedException.class, () -> map.place(new Animal(new Vector2D(5,5))));
    }


    @Test
    public void isOccupiedTest() {
        Animal temp = new Animal(SAMPLEVECTOR);
        assertFalse(map.isOccupied(SAMPLEVECTOR));
        try {
            map.place(temp);
        } catch(PositionAlreadyOccupiedException e) {
            e.printStackTrace();
        }
        assertTrue(map.isOccupied(SAMPLEVECTOR));
        map.move(temp,FORWARD);
        assertFalse(map.isOccupied(SAMPLEVECTOR));
    }

    @Test
    public void canMoveToTest() {
        assertTrue(map.canMoveTo(SAMPLEVECTOR));
        Animal temp = new Animal(SAMPLEVECTOR);
        try {
            map.place(temp);
        } catch(PositionAlreadyOccupiedException e) {
            e.printStackTrace();
        }
        assertFalse(map.canMoveTo(SAMPLEVECTOR));
        assertFalse(map.canMoveTo(new Vector2D(5,5)));
    }

    @Test
    public void objectAtTest() {
        assertEquals(map.objectAt(SAMPLEVECTOR),null);
        Animal temp = new Animal(SAMPLEVECTOR);
        try {
            map.place(temp);
        } catch(PositionAlreadyOccupiedException e) {
            e.printStackTrace();
        }
        assertEquals(map.objectAt(SAMPLEVECTOR),temp);
        map.move(temp,RIGHT);
        assertEquals(map.objectAt(SAMPLEVECTOR),temp);
        map.move(temp,FORWARD);
        assertNotEquals(map.objectAt(SAMPLEVECTOR),temp);
    }
}
