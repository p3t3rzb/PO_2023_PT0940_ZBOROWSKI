package agh.ics.projektC2.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalMultiMapTest {

    @Test
    void getTest() {
        AnimalMultiMap map = new AnimalMultiMap();
        Animal animal1 = new Animal(new Vector2D(2,2),100, List.of(0,0,0));
        Animal animal2 = new Animal(new Vector2D(2,3),200, List.of(0,0,0));
        Animal animal3 = new Animal(new Vector2D(2,2),200, List.of(0,0,0));
        map.put(animal1.getPosition(),animal1);
        map.put(animal2.getPosition(),animal2);
        map.put(animal3.getPosition(),animal3);
        assertEquals(map.get(new Vector2D(2,3)).get(0),animal2);
        assertEquals(map.get(new Vector2D(2,2)).get(0),animal1);
        assertEquals(map.get(new Vector2D(2,2)).get(1),animal3);
    }

    @Test
    void removeTest() {
        AnimalMultiMap map = new AnimalMultiMap();
        Animal animal1 = new Animal(new Vector2D(2,2),100, List.of(0,0,0));
        Animal animal2 = new Animal(new Vector2D(2,3),200, List.of(0,0,0));
        Animal animal3 = new Animal(new Vector2D(2,2),200, List.of(0,0,0));
        map.put(animal1.getPosition(),animal1);
        map.put(animal2.getPosition(),animal2);
        map.put(animal3.getPosition(),animal3);
        map.remove(animal1.getPosition(),animal1);
        assertEquals(map.get(new Vector2D(2,2)).get(0),animal3);
    }
}
