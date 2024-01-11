package agh.ics.projektC2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SwapMutation implements Mutation {
    private static final Random PRNG = new Random();

    @Override
    public void mutateGenome(List<Integer> genome, int minMutationCount, int maxMutationCount) {
        int howMany = maxMutationCount != 0 ? minMutationCount + PRNG.nextInt(maxMutationCount-minMutationCount) : 0;
        List<Integer> oneToN = new ArrayList<>();
        for(int i=0; i<genome.size(); i++) {
            oneToN.add(i);
        }
        Collections.shuffle(oneToN);
        for(int i=0; i<howMany; i+=2) {
            Integer temp = genome.get(oneToN.get(i+1));
            genome.set(oneToN.get(i+1),genome.get(oneToN.get(i)));
            genome.set(oneToN.get(i),temp);
        }

    }
}
