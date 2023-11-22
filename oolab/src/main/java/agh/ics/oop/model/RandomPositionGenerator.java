package agh.ics.oop.model;

import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Math.random;

class CustomIterator implements Iterator<Vector2D> {
    private List<Vector2D> positions;
    private int n;
    private int pos = 0;

    public CustomIterator(RandomPositionGenerator obj) {
        this.n = obj.getPositions().size();
        positions = obj.getPositions();
    }

    @Override
    public Vector2D next() {
        if(hasNext()) {
            pos++;
            return positions.get(pos-1);
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return pos < n;
    }
}
public class RandomPositionGenerator implements Iterable<Vector2D> {
    private List<Vector2D> positions;

    public List<Vector2D> getPositions() {
        return Collections.unmodifiableList(positions);
    }

    @Override
    public Iterator<Vector2D> iterator() {
        return new CustomIterator(this);
    }

    private List<Vector2D> randomPermutation(int width, int height) { // n numbers from 0 to n
        List<Vector2D> permutation = new ArrayList<>();

        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                permutation.add(new Vector2D(i,j));
            }
        }

        Collections.shuffle(permutation);

        return permutation;
    }

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount) {
        positions = new ArrayList<>(grassCount);
        List<Vector2D> temp = randomPermutation(maxWidth,maxHeight);

        for(int i=0; i<grassCount; i++) {
            positions.add(temp.get(i));
        }
    }
}
