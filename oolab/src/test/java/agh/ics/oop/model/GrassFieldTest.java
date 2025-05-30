package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static agh.ics.oop.model.MoveDirection.FORWARD;
import static agh.ics.oop.model.MoveDirection.RIGHT;
import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {
    private WorldMap map = new GrassField(5);
    private static final Vector2D SAMPLEVECTOR = new Vector2D(2,2);

    @Test
    public void placeTest() {
        assertDoesNotThrow(() -> map.place(new Animal(SAMPLEVECTOR)));
        assertThrows(PositionAlreadyOccupiedException.class,() -> map.place(new Animal(SAMPLEVECTOR)));
        assertDoesNotThrow(() -> map.place(new Animal(new Vector2D(1000000,5))));
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
    public void getOrderedAnimalsTest() {
        try {
            map.place(new Animal(new Vector2D(3,3)));
            map.place(new Animal(new Vector2D(2,3)));
            map.place(new Animal(new Vector2D(3,2)));
            map.place(new Animal(SAMPLEVECTOR));
            List<Animal> animals = map.getOrderedAnimals();
            assertEquals(animals.get(0).getPosition(),SAMPLEVECTOR);
            assertEquals(animals.get(1).getPosition(),new Vector2D(2,3));
            assertEquals(animals.get(2).getPosition(),new Vector2D(3,2));
            assertEquals(animals.get(3).getPosition(),new Vector2D(3,3));
        } catch(PositionAlreadyOccupiedException e) {
            e.printStackTrace();
        }
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
        assertTrue(map.canMoveTo(new Vector2D(5,5)));
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
