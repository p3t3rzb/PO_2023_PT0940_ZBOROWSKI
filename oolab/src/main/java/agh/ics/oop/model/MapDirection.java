package agh.ics.oop.model;

public enum MapDirection {
    NORTH(new Vector2D(0,1),"Północ"),
    EAST(new Vector2D(1,0),"Wschód"),
    SOUTH(new Vector2D(0,-1),"Południe"),
    WEST(new Vector2D(-1,0),"Zachód");

    private Vector2D directionVector;
    private String directionName;

    MapDirection(Vector2D directionVector, String directionName) {
        this.directionVector = directionVector;
        this.directionName = directionName;
    }


    public MapDirection next() {
        return switch(this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public MapDirection previous() {
        return switch(this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }

    public Vector2D toUnitVector() {
        return directionVector;
    }

    public String toString() {
        return directionName;
    }
}
