package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {
    @Test
    public void equalsTest() {
        ;
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
        ;
    }

    @Test
    public void lowerLeftTest() {
        ;
    }

    @Test
    public void addTest() {
        ;
    }

    @Test
    public void subtractTest() {
        ;
    }

    @Test
    public void oppositeTest() {
        assertEquals(new Vector2D(-1,2).opposite(),new Vector2D(1,-2));
        assertEquals(new Vector2D(3,5).opposite(),new Vector2D(-3,-5));
        assertEquals(new Vector2D(21,22).opposite(),new Vector2D(-21,-22));
    }
}
