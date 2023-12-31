package agh.ics.projektC2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SwapMutation implements Mutation {
    private static final Random PRNG = new Random();

    @Override
    public void mutateGenome(List<Integer> genome) {
        List<Integer> oneToN = new ArrayList<>();
        for(int i=0; i<genome.size(); i++) {
            oneToN.add(i);
        }
        Collections.shuffle(oneToN);
        Integer temp = genome.get(oneToN.get(1));
        genome.set(oneToN.get(1),genome.get(oneToN.get(0)));
        genome.set(oneToN.get(0),temp);
    }
}
