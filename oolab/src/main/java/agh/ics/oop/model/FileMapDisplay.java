package agh.ics.oop.model;

import java.io.*;
import java.util.Scanner;

public class FileMapDisplay implements MapChangeListener {
    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        File file = new File("map_" + worldMap.getID() + ".log");
        try(FileWriter fileWriter = new FileWriter(file,true)) {
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(message + "\n");
        } catch (IOException e) {
            System.out.println("Couldn't write to log");
        }
    }
}
