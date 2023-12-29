package agh.ics.projektC2.model;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2D> {
    private List<Vector2D> positions;
    private static final Random PRNG = new Random();

    @Override
    public Iterator<Vector2D> iterator() {
        return positions.iterator();
    }

    private List<Vector2D> randomPermutation(int minWidth, int maxWidth, int minHeight, int maxHeight) { // n numbers from 0 to n
        List<Vector2D> permutation = new ArrayList<>();

        for(int i=minWidth; i<maxWidth; i++) {
            for(int j=minHeight; j<maxHeight; j++) {
                permutation.add(new Vector2D(i,j));
            }
        }

        Collections.shuffle(permutation);

        return permutation;
    }

    public RandomPositionGenerator(List<Vector2D> preferredPositions, List<Vector2D> notPreferredPositions, int count) {
        Collections.shuffle(preferredPositions);
        Collections.shuffle(notPreferredPositions);
        int i=0,j=0;

        while(i+j<count) {
            if(PRNG.nextInt(5) == 0 && i < notPreferredPositions.size()) {
                positions.add(notPreferredPositions.get(i));
                i++;
            } else if(j < preferredPositions.size()) {
                positions.add(preferredPositions.get(j));
                j++;
            }
        }
    }
}