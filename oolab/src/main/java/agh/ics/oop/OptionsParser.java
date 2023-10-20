package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser {
    public static MoveDirection[] parse(String[] args) {
        MoveDirection[] result = new MoveDirection[args.length];
        int j = 0;

        for(int i=0; i<args.length; i++) {
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
                default:
                    j--;
                    break;
            }
            j++;
        }

        return result;
    }
}
