package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener {
    private int count = 0;
    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        count++;
        System.out.println(message);
        System.out.println("Map ID: " + String.valueOf(worldMap.getID()));
        System.out.println(worldMap.toString());
        System.out.println("Updates so far: " + String.valueOf(count));
    }
}
