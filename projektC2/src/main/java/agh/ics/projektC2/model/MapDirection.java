package agh.ics.projektC2.model;

public enum MapDirection {
    NORTH(new Vector2D(0,1),"Północ",0),
    NORTHEAST(new Vector2D(1,1), "Północny wschód",1),
    EAST(new Vector2D(1,0),"Wschód",2),
    SOUTHEAST(new Vector2D(1,-1), "Południowy wschód",3),
    SOUTH(new Vector2D(0,-1),"Południe",4),
    SOUTHWEST(new Vector2D(-1,-1), "Południowy zachód",5),
    WEST(new Vector2D(-1,0),"Zachód",6),
    NORTHWEST(new Vector2D(-1,1), "Północny zachód",7);


    private static MapDirection values[] = values();
    private Vector2D directionVector;
    private String directionName;
    private int rotation;

    MapDirection(Vector2D directionVector, String directionName, int rotation) {
        this.rotation = rotation;
        this.directionVector = directionVector;
        this.directionName = directionName;
    }


    public MapDirection next(int rotationChange) {
        return values[(8+rotation+rotationChange%8)%8];
    }

    public MapDirection previous(int rotationChange) {
        return values[(8+rotation-rotationChange%8)%8];
    }

    public Vector2D toUnitVector() {
        return directionVector;
    }

    public String toString() {
        return directionName;
    }
}
