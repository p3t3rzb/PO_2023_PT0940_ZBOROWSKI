package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser {
    public static MoveDirection[] parse(String[] args) {
        MoveDirection[] result = new MoveDirection[args.length];

        for(int i=0, j=0; i<args.length; i++, j++) {
            switch(args[i]) {
                case "f":
                    result[i] = MoveDirection.FORWARD;
                case "b":
                    result[i] = MoveDirection.BACKWARD;
                case "r":
                    result[i] = MoveDirection.RIGHT;
                case "l":
                    result[i] = MoveDirection.LEFT;
                default:
                    j--;
            }
        }

        return result;
    }
}
