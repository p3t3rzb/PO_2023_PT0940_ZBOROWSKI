package agh.ics.projektC2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RandomPositionGenerator implements Iterable<Vector2D> {
    private List<Vector2D> positions;

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

    public RandomPositionGenerator(int minWidth, int maxWidth, int minHeight, int maxHeight, int count) {
        positions = new ArrayList<>(count);
        List<Vector2D> temp = randomPermutation(minWidth,maxWidth,minHeight,maxHeight);

        for(int i=0; i<count; i++) {
            positions.add(temp.get(i));
        }
    }
}