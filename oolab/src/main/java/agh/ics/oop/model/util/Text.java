package agh.ics.oop.model.util;

import agh.ics.oop.model.MapDirection;

public class Text {
    private String word;
    private MapDirection orientation;

    public Text(String word, MapDirection orientation) {
        this.word = word;
        this.orientation = orientation;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public String getWord() {
        return word;
    }

    public void setOrientation(MapDirection orientation) {
        this.orientation = orientation;
    }
}
