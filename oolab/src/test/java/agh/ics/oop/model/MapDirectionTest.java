package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static agh.ics.oop.model.MapDirection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MapDirectionTest {
    @Test
    public void nextTest() {
        assertEquals(NORTH.next(),EAST);
        assertEquals(EAST.next(),SOUTH);
        assertEquals(SOUTH.next(),WEST);
        assertEquals(WEST.next(),NORTH);
    }

    @Test
    public void previousTest() {
        assertEquals(NORTH.previous(),WEST);
        assertEquals(EAST.previous(),NORTH);
        assertEquals(SOUTH.previous(),EAST);
        assertEquals(WEST.previous(),SOUTH);
    }
}
