package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import java.util.ArrayList;
import java.util.List;

import static agh.ics.oop.model.MoveDirection.*;

public class OptionsParser {
    public static List<MoveDirection> parse(String[] args) {
        List<MoveDirection> result = new ArrayList<>();

        for(String arg: args) {
            switch(arg) {
                case "f": result.add(FORWARD); break;
                case "b": result.add(BACKWARD); break;
                case "r": result.add(RIGHT); break;
                case "l": result.add(LEFT); break;
                default: throw new IllegalArgumentException(arg + " is not legal move specification");
            }
        }

        return result;
    }
}
