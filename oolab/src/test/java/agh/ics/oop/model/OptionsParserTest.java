package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionsParserTest {
    @Test
    public void parseTest() {
        String[] instructions = "f b r l k b mmdw b r f f".split(" ");
        MoveDirection[] moves = OptionsParser.parse(instructions);

        for(int i=0, j=0; i<instructions.length && moves[i] != null; i++,j++) {
            if(instructions[j].equals("f") || instructions[j].equals("b") ||instructions[j].equals("r") ||instructions[j].equals("l")) {
                assertEquals(moves[i].toString(),instructions[j]);
            } else {
                i--;
            }
        }
    }
}
