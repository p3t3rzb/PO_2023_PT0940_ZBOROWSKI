package agh.ics.projektC2.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector2DTest {
    @Test
    public void equalsTest() {
        assertTrue(new Vector2D(-1,2).equals(new Vector2D(-1,2)));
        assertFalse(new Vector2D(-1,2).equals(new Vector2D(-1,-1)));
    }

    @Test
    public void toStringTest() {
        assertEquals(new Vector2D(-1,2).toString(),"(-1,2)");
        assertEquals(new Vector2D(3,5).toString(),"(3,5)");
        assertEquals(new Vector2D(21,22).toString(),"(21,22)");
    }

    @Test
    public void precedesTest() {
        assertTrue(new Vector2D(-1,2).precedes(new Vector2D(0,3)));
        assertTrue(new Vector2D(21,22).precedes(new Vector2D(21,22)));
        assertFalse(new Vector2D(-1,2).precedes(new Vector2D(-5,3)));
    }

    @Test
    public void followsTest() {
        assertTrue(new Vector2D(-1,2).follows(new Vector2D(-4,2)));
        assertTrue(new Vector2D(21,22).follows(new Vector2D(21,22)));
        assertFalse(new Vector2D(-1,2).follows(new Vector2D(5,1)));
    }

    @Test
    public void upperRightTest() {
        assertEquals(new Vector2D(-1,2).upperRight(new Vector2D(-4,2)), new Vector2D(-1,2));
        assertEquals(new Vector2D(21,22).upperRight(new Vector2D(21,22)), new Vector2D(21,22));
        assertEquals(new Vector2D(-1,2).upperRight(new Vector2D(5,1)), new Vector2D(5,2));
    }

    @Test
    public void lowerLeftTest() {
        assertEquals(new Vector2D(-1,2).lowerLeft(new Vector2D(-4,2)), new Vector2D(-4,2));
        assertEquals(new Vector2D(21,22).lowerLeft(new Vector2D(21,22)), new Vector2D(21,22));
        assertEquals(new Vector2D(-1,2).lowerLeft(new Vector2D(5,1)), new Vector2D(-1,1));
    }

    @Test
    public void addTest() {
        assertEquals(new Vector2D(-1,2).add(new Vector2D(-4,2)), new Vector2D(-5,4));
        assertEquals(new Vector2D(21,22).add(new Vector2D(21,22)), new Vector2D(42,44));
        assertEquals(new Vector2D(-1,2).add(new Vector2D(5,1)), new Vector2D(4,3));
    }

    @Test
    public void subtractTest() {
        assertEquals(new Vector2D(-1,2).subtract(new Vector2D(-4,2)), new Vector2D(3,0));
        assertEquals(new Vector2D(21,22).subtract(new Vector2D(21,22)), new Vector2D(0,0));
        assertEquals(new Vector2D(-1,2).subtract(new Vector2D(5,1)), new Vector2D(-6,1));
    }

    @Test
    public void oppositeTest() {
        assertEquals(new Vector2D(-1,2).opposite(),new Vector2D(1,-2));
        assertEquals(new Vector2D(3,5).opposite(),new Vector2D(-3,-5));
        assertEquals(new Vector2D(21,22).opposite(),new Vector2D(-21,-22));
    }
}
