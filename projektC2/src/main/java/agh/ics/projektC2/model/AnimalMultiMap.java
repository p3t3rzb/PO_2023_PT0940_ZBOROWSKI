package agh.ics.projektC2.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

    public List<Animal> values() {
        List<Animal> animals = new ArrayList<>();
        for(ArrayList<Animal> list : map.values()) {
            animals.addAll(list);
        }

        return animals;
    }

    public Set<Vector2D> keySet() {
        return map.keySet();
    }
}
