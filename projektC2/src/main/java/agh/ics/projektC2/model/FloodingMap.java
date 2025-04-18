package agh.ics.projektC2.model;

import java.util.*;

import static java.util.Collections.max;

public class FloodingMap extends AbstractWorldMap {
    private final Vector2D mapStart = new Vector2D(0,0);
    private final Vector2D mapEnd;
    private final HashMap<Vector2D,Water> waters = new HashMap<>();
    private final Set<Vector2D> forbiddenForWaters = new HashSet<>();
    private final HashMap<Vector2D,Water> waterSources = new HashMap<>();
    private int floodingsCount = 0;
    private final int floodingsNo;
    private final int maxFloodRadius = 4;

    public FloodingMap(int width, int height, int plantEnergy, int satisfactoryEnergy, int requiredEnergy, Mutation mutation, int minMutationCount, int maxMutationCount, int initialPlants) {
        super(plantEnergy,satisfactoryEnergy,requiredEnergy,mutation,minMutationCount,maxMutationCount);
        mapEnd = new Vector2D(width-1,height-1);
        transformation = new IdentityTransformation();
        growth = new EquatorGrowth(mapStart, mapEnd, forbiddenForPlants);
        forbiddenForAnimals = forbiddenForWaters;

        floodingsNo = height*width/30;
        List<Vector2D> positions = new PositionGenerator(getCurrentBounds(),forbiddenForWaters).getPositions();
        positions = new RandomPositionGenerator(positions,new ArrayList<>(),floodingsNo).getPositions(); // count - ile ma być zbiorników
        for(Vector2D position : positions) {
            Water water = new Water(position);
            waters.put(position,water);
            waterSources.put(position,water);
            forbiddenForWaters.add(position);
        }

        addPlants(initialPlants);
    }

    private Water waterAt(Vector2D position) {
        return waters.get(position);
    };

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(mapStart,mapEnd);
    }

    @Override
    public void place(Animal animal) throws PositionAlreadyOccupiedException {
        super.place(animal);
        forbiddenForWaters.add(animal.getPosition());
    }

    private void expandWaters() {
        List<Vector2D> toAdd = new ArrayList<>();
        for(Vector2D position : waters.keySet()) {
            for(Vector2D adjacentWaterPosition : position.adjacent()) {
                if(!forbiddenForWaters.contains(adjacentWaterPosition)) {
                    toAdd.add(adjacentWaterPosition);
                }
            }
        }

        for(Vector2D position : toAdd) {
            waters.put(position,new Water(position));
            forbiddenForWaters.add(position);
        }
    }

    private void shrinkWaters() {
        List<Vector2D> toRemove = new ArrayList<>();
        for(Vector2D position : waters.keySet()) {
            int count = 0;
            for(Vector2D adjacentWaterPosition : position.adjacent()) {
                if(waterAt(adjacentWaterPosition) != null) {
                    count++;
                }
            }

            if((count < 4 || count == 0) && waterSources.get(position) == null) {
                toRemove.add(position);
            }
        }

        for(Vector2D position : toRemove) {
            waters.remove(position);
            forbiddenForWaters.remove(position);
        }
    }

    @Override
    public WorldElement objectAt(Vector2D position) {
        Water water = waterAt(position);
        if(water != null) {
            return water;
        } else {
            List<Animal> objectsAt = animals.get(position);
            return objectsAt.isEmpty() ? plantAt(position) : max(objectsAt);
        }
    }

    @Override
    public void move() {
        day++;
        for(Animal animal : getAnimals()) {
            Vector2D position = animal.getPosition();
            moveAnimal(animal);
            if(objectAt(position) instanceof Plant || objectAt(position) == null) {
                forbiddenForWaters.remove(position);
            }
            forbiddenForWaters.add(animal.getPosition());
        }
        floodingsCount = (floodingsCount+1)%(maxFloodRadius*2-2);
        if(floodingsCount < (maxFloodRadius-1)) {
            expandWaters();
        } else {
            shrinkWaters();
        }
        mapChanged("map changed"); // rozwinąć
    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        return position.follows(mapStart) && position.precedes(mapEnd) && waterAt(position) == null;
    }
}
