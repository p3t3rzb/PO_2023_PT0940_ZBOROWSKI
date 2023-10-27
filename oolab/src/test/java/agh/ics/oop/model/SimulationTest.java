package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationTest {
    @Test
    public void runTest() {
        List<MoveDirection> moves = OptionsParser.parse("f b r l f f r r f f".split(" "));
        List<Vector2D> positions = List.of(new Vector2D(2,2), new Vector2D(3,4));
        Simulation simulation = new Simulation(moves,positions);
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        assertEquals(animals.get(0).toString(),"(3,2) Południe");
        assertEquals(animals.get(1).toString(),"(2,4) Północ");
    }
}
