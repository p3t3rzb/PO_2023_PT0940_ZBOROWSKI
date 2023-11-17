package agh.ics.oop.model;

import agh.ics.oop.model.util.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static agh.ics.oop.model.MapDirection.*;
import static agh.ics.oop.model.MoveDirection.*;

public class TextMap implements WorldMap<String,Integer> {
    private final Map<Integer, Text> map = new HashMap<>();
    private int n;

    public TextMap() {
        n = 0;
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
        if(getText(word) != null) {
            return false;
        }
        map.put(n,new Text(word,EAST));
        n++;
        return true;
    }

    @Override
    public void move(String word, MoveDirection direction) {
        Entry<Integer,Text> text = getText(word);
        if(text == null) {
            return;
        }

        MapDirection orientation = text.getValue().getOrientation();
        Integer newKey = text.getKey(), temp = text.getKey();
        map.remove(text.getKey());

        if((direction == FORWARD && orientation == EAST) || (direction == BACKWARD && orientation == WEST)) {
            temp = Integer.valueOf(newKey.intValue()+1);

        } else if((direction == FORWARD && orientation == WEST) || (direction == BACKWARD && orientation == EAST)) {
            temp = Integer.valueOf(newKey.intValue()-1);
        }

        if(canMoveTo(temp)) {
            map.remove(newKey);
            map.put(newKey,map.get(temp));
            newKey = temp;
        }

        map.put(newKey,text.getValue());
    }

    @Override
    public boolean isOccupied(Integer position) {
        return canMoveTo(position);
    }

    @Override
    public String objectAt(Integer position) {
        if(isOccupied(position)) {
            return map.get(position).getWord();
        }
        return null;
    }

    @Override
    public boolean canMoveTo(Integer position) {
        return position.intValue() >= 0 && position.intValue() < n;
    }

    public void changeOrientation(String word, MapDirection orientation) {
        Text text = getText(word).getValue();
        if(text == null) {
            return;
        }
        text.setOrientation(orientation);
    }

    @Override
    public String toString() {
        String[] result = new String[n];
        for(int i=0; i<n; i++) {
            result[i] = map.get(Integer.valueOf(i)).getWord();
        }

        return String.join(" ",result);
    }
}
