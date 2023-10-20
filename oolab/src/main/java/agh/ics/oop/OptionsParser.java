package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser {
    public static MoveDirection[] Parse(String[] args) {
        MoveDirection[] result = new MoveDirection[args.length];

        for(int i=0; i<args.length; i++) {
            result[i] = MoveDirection.OTHER;
            switch(args[i]) {
                case "f":
                    result[i] = MoveDirection.FORWARD;
                    break;
                case "b":
                    result[i] = MoveDirection.BACKWARD;
                    break;
                case "r":
                    result[i] = MoveDirection.RIGHT;
                    break;
                case "l":
                    result[i] = MoveDirection.LEFT;
                    break;
            }
        }

        return result;
    }
}
