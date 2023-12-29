package agh.ics.projektC2.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimalMultiMap {
    private final HashMap<Vector2D, ArrayList<Animal>> map = new HashMap<>();

    public void put(Vector2D key, Animal value) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    public List<Animal> get(Vector2D key) {
        return map.getOrDefault(key, new ArrayList<>());
    }

    public void remove(Vector2D key, Animal value) {
        map.computeIfPresent(key, (k, v) -> {
            v.remove(value);
            return v;
        });
    }
}
