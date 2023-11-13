package agh.ics.oop.model;

import agh.ics.oop.model.util.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static agh.ics.oop.model.MapDirection.*;
import static agh.ics.oop.model.MoveDirection.*;

public class TextMap implements WorldMap<String,Integer> {
    private Map<Integer, Text> map = new HashMap<>();
    private int N;

    public TextMap() {
        N = 0;
    }

    private Entry<Integer,Text> getText(String word) {
        for(Entry<Integer,Text> entry: map.entrySet()) {
            if(entry.getValue().getWord().equals(word)) {
                return entry;
            }
        }
        return null;
    }

    @Override
    public boolean place(String word) {
        map.put(N,new Text(word,EAST));
        N++;
        return true;
    }

    @Override
    public void move(String word, MoveDirection direction) {
        Entry<Integer,Text> text = getText(word);
        MapDirection orientation = text.getValue().getOrientation();
        Integer newKey = text.getKey();
        map.remove(text.getKey());

        if((direction == FORWARD && orientation == EAST) || (direction == BACKWARD && orientation == WEST)) {
            Integer temp = Integer.valueOf(newKey.intValue()+1);
            if(canMoveTo(temp)) {
                newKey = temp;
            }
        } else if((direction == FORWARD && orientation == WEST) || (direction == BACKWARD && orientation == EAST)) {
            Integer temp = Integer.valueOf(newKey.intValue()-1);
            if(canMoveTo(temp)) {
                newKey = temp;
            }
        }

        map.put(newKey,text.getValue());
    }

    @Override
    public boolean isOccupied(Integer position) {
        return position.intValue() < N;
    }

    @Override
    public String objectAt(Integer position) {
        return map.get(position).getWord();
    }

    @Override
    public boolean canMoveTo(Integer position) {
        return position.intValue() >= 0 && position.intValue() < N;
    }

    public void changeOrientation(String word, MapDirection orientation) {
        Text text = getText(word).getValue();
        text.setOrientation(orientation);
    }
}
