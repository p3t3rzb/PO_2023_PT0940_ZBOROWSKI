package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser {
    public static MoveDirection[] parse(String[] args) {
        MoveDirection[] result = new MoveDirection[args.length];

        for(int i=0, j=0; i<args.length; i++, j++) {
            switch(args[i]) {
                case "f":
                    result[j] = MoveDirection.FORWARD;
                    break;
                case "b":
                    result[j] = MoveDirection.BACKWARD;
                    break;
                case "r":
                    result[j] = MoveDirection.RIGHT;
                    break;
                case "l":
                    result[j] = MoveDirection.LEFT;
                    break;
                default:
                    j--;
                    break;
            }
        }

        return result;
    }
}
