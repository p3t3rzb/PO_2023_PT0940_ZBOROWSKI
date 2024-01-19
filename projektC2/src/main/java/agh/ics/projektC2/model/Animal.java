package agh.ics.projektC2.model;


import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.max;

public class Animal implements WorldElement, Comparable<Animal> {
    private MapDirection orientation;
    private Vector2D position;
    private final List<Integer> genome;
    private final List<Animal> parents = new ArrayList<>(2);
    private int energy;
    private int currentGene;
    private int age = 0;
    private int childrenNo = 0;
    private int descendantsNo = 0;
    private int deathDay = -1;
    private int plantsEaten = 0;
    private boolean dead = false;
    private static final Random PRNG = new Random();

    public boolean isDead() {
        return dead;
    }

    public int getDeathDay() {
        return deathDay;
    }

    public Animal(Vector2D position, int energy, List<Integer> genome) {
        this.position = position;
        this.energy = energy;
        this.genome = genome;
        currentGene = PRNG.nextInt(max(1,genome.size()));
        orientation = MapDirection.randomDirection();
    }

    public Animal generateChild(Animal secondParent, int requiredEnergy, Mutation mutation, int minMutationCount, int maxMutationCount) {
        energy -= requiredEnergy;
        secondParent.childrenNo++;
        secondParent.energy -= requiredEnergy;
        List<Integer> newGenome = new ArrayList<>(genome.size());
        int fromFirstParent = (int)(genome.size()*energy/(secondParent.energy+energy));

        if(PRNG.nextInt(2) == 0) {
            for (int i = 0; i < fromFirstParent; i++) {
                newGenome.add(genome.get(i));
            }
            for (int i = fromFirstParent; i < genome.size(); i++) {
                newGenome.add(secondParent.genome.get(i));
            }
        } else {
            for(int i=0; i<genome.size()-fromFirstParent; i++) {
                newGenome.add(secondParent.genome.get(i));
            }
            for(int i=genome.size()-fromFirstParent; i<genome.size(); i++) {
                newGenome.add(genome.get(i));
            }
        }

        mutation.mutateGenome(newGenome,minMutationCount,maxMutationCount);

        Animal child = new Animal(position,requiredEnergy*2,newGenome);

        this.childrenNo++;
        secondParent.childrenNo++;
        child.parents.add(this);
        child.parents.add(secondParent);

        Set<Animal> tried = new HashSet<>();
        Stack<Animal> ancestors = new Stack<>();
        ancestors.push(this);
        ancestors.push(secondParent);
        while(!ancestors.empty()) {
            Animal current = ancestors.pop();
            tried.add(current);
            current.descendantsNo++;
            for(Animal parent : current.parents) {
                if(!tried.contains(parent)) {
                    ancestors.push(parent);
                }
            }
        }

        return child;
    }

    public int getCurrentGene() {
        return genome.get(currentGene);
    }

    public int getPlantsEaten() {
        return plantsEaten;
    }

    public void setPlantsEaten(int plantsEaten) {
        this.plantsEaten = plantsEaten;
    }

    public String getGenome() {
        return genome.stream()
                .map(Object::toString)
                .collect(Collectors.joining(""));
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public int compareTo(Animal otherAnimal) {
        int result = Integer.compare(this.getEnergy(),otherAnimal.getEnergy());
        if(result == 0) {
            result = Integer.compare(this.getAge(),otherAnimal.getAge());
            if(result == 0) {
                result = Integer.compare(this.getChildrenNo(),otherAnimal.getChildrenNo());
            }
        }

        return result;
    }

    public int getChildrenNo() {
        return childrenNo;
    }

    public int getDescendantsNo() {
        return descendantsNo;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String getImageFile() {
        return switch(orientation) {
            case EAST -> "right.png";
            case NORTH -> "up.png";
            case WEST -> "left.png";
            case SOUTH -> "down.png";
            case NORTH_EAST -> "upright.png";
            case SOUTH_EAST -> "rightdown.png";
            case NORTH_WEST -> "upleft.png";
            case SOUTH_WEST -> "downleft.png";
        };
    }

    @Override
    public String toString() {
        return switch(orientation) {
            case NORTH, NORTH_EAST -> "^";
            case EAST, SOUTH_EAST -> ">";
            case SOUTH, SOUTH_WEST -> "V";
            case WEST, NORTH_WEST -> "<";
        };
    }

    @Override
    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }

    public void die(int deathDay) {
        dead = true;
        this.deathDay = deathDay;
    }

    public void move(MoveValidator validator, MoveTransformation transformation) {
        MoveDirection direction = MoveDirection.intToDirection(genome.get(currentGene).intValue());

        currentGene = currentGene+1;
        if(currentGene == genome.size()) {
            currentGene = 0;
        }

        Vector2D temp;
        orientation = orientation.rotate(direction);
        temp = position.add(orientation.toUnitVector());

        // transformation for individual maps
        OrientedPosition orientedPosition = new OrientedPosition(temp,orientation);
        orientedPosition = transformation.transform(orientedPosition,orientation);
        temp = orientedPosition.position();
        orientation = orientedPosition.orientation();

        if(validator.canMoveTo(temp)) {
            position = temp;
        }

        age++;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }
}
