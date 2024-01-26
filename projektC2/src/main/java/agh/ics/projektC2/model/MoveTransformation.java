package agh.ics.projektC2.model;

public interface MoveTransformation {
    OrientedPosition transform(OrientedPosition currentPosition, MapDirection lastMove); // skoro currentPosition już ma orientację, to po co jeszcze MapDirection?
}
