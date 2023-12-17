package agh.ics.oop.presenter;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SimulationPresenter implements MapChangeListener {
    WorldMap map;

    @FXML
    private Label infoLabel;

    public void setWorldMap(WorldMap map) {
        this.map = map;
    }

    private void drawMap(WorldMap map) {
        infoLabel.setText(map.toString());
    }

    @Override
    public void mapChanged(WorldMap map, String text) {
        Platform.runLater(() -> drawMap(map));
    }
}
