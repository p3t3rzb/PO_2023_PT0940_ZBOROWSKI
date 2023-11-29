package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;
import org.junit.jupiter.api.Test;
import java.util.List;

import static agh.ics.oop.model.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OptionsParserTest {
    @Test
    public void parseTest() {
        assertThrows(IllegalArgumentException.class,() -> OptionsParser.parse("f b r l k b mmdw b r f f".split(" ")));

        String[] instructions = "f b r l b b r f f".split(" ");
        List<MoveDirection> moves = OptionsParser.parse(instructions);
        List<MoveDirection> model = List.of(FORWARD, BACKWARD, RIGHT, LEFT, BACKWARD, BACKWARD, RIGHT, FORWARD, FORWARD);

        for(int i=0; i<moves.size(); i++) {
            assertEquals(moves.get(i),model.get(i));
        }
    }
}
