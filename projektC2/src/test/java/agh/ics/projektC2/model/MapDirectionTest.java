package agh.ics.projektC2.model;

import org.junit.jupiter.api.Test;

import static agh.ics.projektC2.model.MapDirection.*;
import static agh.ics.projektC2.model.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MapDirectionTest {
    @Test
    public void rotateTest() {
        assertEquals(NORTH.rotate(RIGHT),EAST);
        assertEquals(SOUTH.rotate(FORWARD_RIGHT),SOUTH_WEST);
        assertNotEquals(NORTH.rotate(RIGHT),NORTH);
        assertNotEquals(SOUTH.rotate(FORWARD_RIGHT),WEST);
    }
}
