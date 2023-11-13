package agh.ics.oop.model;

import agh.ics.oop.model.util.Text;
import org.junit.jupiter.api.Test;

import static agh.ics.oop.model.MapDirection.*;
import static agh.ics.oop.model.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.*;

public class TextMapTest {
    @Test
    void placeTest() {
        TextMap map = new TextMap();
        map.place("Ala");
        map.place("Ma");
        map.place("Sowoniedźwiedzia");

        assertEquals("Ala Ma Sowoniedźwiedzia",map.toString());
    }

    @Test
    void moveTest() {
        TextMap map = new TextMap();
        map.place("Ala");
        map.place("Ma");
        map.place("Sowoniedźwiedzia");
        map.move("Ma",FORWARD);
        assertEquals("Ala Sowoniedźwiedzia Ma",map.toString());
        map.move("Ma",FORWARD);
        map.move("Ala",BACKWARD);
        assertEquals("Ala Sowoniedźwiedzia Ma",map.toString());
        map.changeOrientation("Ala",WEST);
        map.move("Ala",BACKWARD);
        map.move("Ala",BACKWARD);
        assertEquals("Sowoniedźwiedzia Ma Ala",map.toString());
        map.changeOrientation("Sowoniedźwiedzia",NORTH);
        map.move("Sowoniedźwiedzia",FORWARD);
        assertEquals("Sowoniedźwiedzia Ma Ala",map.toString());
    }


    @Test
    void objectAtTest() {
        TextMap map = new TextMap();
        map.place("Ala");
        map.place("Ma");
        map.place("Sowoniedźwiedzia");
        assertEquals(map.objectAt(2),"Sowoniedźwiedzia");
    }

    @Test
    void canMoveToTest() {
        TextMap map = new TextMap();
        map.place("Ala");
        map.place("Ma");
        map.place("Sowoniedźwiedzia");
        assertTrue(map.isOccupied(2));
        assertFalse(map.isOccupied(-1));
        assertFalse(map.isOccupied(3));
    }
}
