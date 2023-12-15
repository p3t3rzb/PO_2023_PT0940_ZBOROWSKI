package agh.ics.oop.presenter;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SimulationPresenter implements MapChangeListener {
    WorldMap map;
    @FXML
    private Label infoLabel;

    public void setWorldMap(WorldMap map) {
        this.map = map;
    }

    private void drawMap() {
        infoLabel.setText(map.toString());
    }

    public void mapChanged(WorldMap map, String text) {
        setWorldMap(map);
        drawMap();
    }
}
