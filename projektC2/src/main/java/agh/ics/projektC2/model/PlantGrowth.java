package agh.ics.projektC2.model;

import java.util.List;

public interface PlantGrowth {
    List<Vector2D> positions(int count);

    Boundary getPreferredBoundary();
}
