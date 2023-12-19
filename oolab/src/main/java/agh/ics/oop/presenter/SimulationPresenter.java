package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

import static java.lang.Math.abs;

public class SimulationPresenter implements MapChangeListener {
    private static final int CELL_HEIGHT = 40;
    private static final int CELL_WIDTH = 40;
    private WorldMap map;

    @FXML
    private Label infoLabel, lastMove;
    @FXML
    private TextField infoText;
    @FXML
    private Button infoButton;
    @FXML
    private GridPane mapGrid;

    private void setWorldMap(WorldMap map) {
        this.map = map;
    }

    private void drawMap(WorldMap map) {
        clearGrid();
        Boundary currentBounds = map.getCurrentBounds();
        int width = currentBounds.upperRightCorner().getX()-currentBounds.bottomLeftCorner().getX()+1;
        int height = currentBounds.upperRightCorner().getY()-currentBounds.bottomLeftCorner().getY()+1;

        for(int i=0; i<height; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
        for(int i=0; i<width; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Label child = new Label();
                Vector2D childPosition = new Vector2D(x+currentBounds.bottomLeftCorner().getX(),y+currentBounds.bottomLeftCorner().getY());
                WorldElement element = map.objectAt(childPosition);
                if(element != null) {
                    child.setText(element.toString());
                }
                mapGrid.add(child,x,height-y-1); // odwrócona orientacja przez wymogi zadania
                mapGrid.setHalignment(child, HPos.CENTER);
            }
        }

        //infoLabel.setText(map.toString());
    }

    @Override
    public void mapChanged(WorldMap map, String message) {
        Platform.runLater(() -> {
            drawMap(map);
            lastMove.setText(message);
        });
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) {
        List<Vector2D> positions = List.of(new Vector2D(2,2), new Vector2D(3,4));
        List<MoveDirection> directions = OptionsParser.parse(infoText.getText().split(" "));
        GrassField grassMap = new GrassField(10);
        setWorldMap(grassMap);
        grassMap.addObserver(this);
        new SimulationEngine(List.of(new Simulation(directions, positions, grassMap))).runAsync();
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}
