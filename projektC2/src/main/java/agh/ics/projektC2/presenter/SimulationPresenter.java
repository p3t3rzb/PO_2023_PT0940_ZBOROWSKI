package agh.ics.projektC2.presenter;

import agh.ics.projektC2.model.MapChangeListener;
import agh.ics.projektC2.model.WorldMap;

public class SimulationPresenter implements MapChangeListener {
    private static final int CELL_HEIGHT = 40;
    private static final int CELL_WIDTH = 40;
    private WorldMap map;

    private void setWorldMap(WorldMap map) {
        this.map = map;
    }

    @Override
    public void mapChanged(WorldMap map, String message) {
        ;
    }

}
