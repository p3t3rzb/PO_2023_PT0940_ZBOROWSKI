package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static agh.ics.oop.model.MoveDirection.*;

public class OptionsParser {
    public static List<MoveDirection> parse(List<String> args) {
        return args.stream()
                .map(arg -> switch(arg) {
                    case "f" -> FORWARD;
                    case "b" -> BACKWARD;
                    case "r" -> RIGHT;
                    case "l" -> LEFT;
                    default -> throw new IllegalArgumentException(arg + " is not legal move specification");
                })
                .collect(Collectors.toList());
    }
}
