package agh.ics.projektC2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FullyRandomMutation implements Mutation {
    private static final Random PRNG = new Random();

    @Override
    public void mutateGenome(List<Integer> genome, int minMutationCount, int maxMutationCount) {
        int howMany = maxMutationCount != minMutationCount ? (maxMutationCount != 0 ? minMutationCount + PRNG.nextInt(maxMutationCount-minMutationCount) : 0) : 0;
        List<Integer> oneToN = new ArrayList<>();
        for(int i=0; i<genome.size(); i++) {
            oneToN.add(i);
        }
        Collections.shuffle(oneToN);
        for(int i=0; i<howMany; i++) {
            genome.set(oneToN.get(i),PRNG.nextInt(8));
        }
    }
}
