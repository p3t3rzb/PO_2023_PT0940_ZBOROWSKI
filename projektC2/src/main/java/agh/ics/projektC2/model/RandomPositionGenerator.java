package agh.ics.projektC2.model;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2D> {
    private List<Vector2D> positions;
    private static final Random PRNG = new Random();

    @Override
    public Iterator<Vector2D> iterator() {
        return positions.iterator();
    }

    public List<Vector2D> getPositions() {
        return positions;
    }

    public RandomPositionGenerator(List<Vector2D> preferredPositions, List<Vector2D> notPreferredPositions, int count) {
        positions = new ArrayList<>();
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
            } else if(i == notPreferredPositions.size() && j == notPreferredPositions.size()) {
                break;
            }
        }
    }
}