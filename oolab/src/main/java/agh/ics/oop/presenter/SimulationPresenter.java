package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.abs;

public class SimulationPresenter implements MapChangeListener {
    private static final int CELL_HEIGHT = 30;
    private static final int CELL_WIDTH = 30;
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

        for(int i=0; i<=height; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
        for(int i=0; i<=width; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
        Label xyLabel = new Label();
        xyLabel.setText("y\\x");
        mapGrid.add(xyLabel,0,0);
        mapGrid.setHalignment(xyLabel, HPos.CENTER);
        for(int x = 1; x <= width; x++) {
            Label child = new Label();
            child.setText(String.valueOf(x-1+currentBounds.bottomLeftCorner().getX()));
            mapGrid.add(child,x,0);
            mapGrid.setHalignment(child, HPos.CENTER);
        }
        for(int y = 1; y <= height; y++) {
            Label child = new Label();
            child.setText(String.valueOf(height-y+currentBounds.bottomLeftCorner().getY())); // odwrócona orientacja przez wymogi zadania
            mapGrid.add(child,0,y);
            mapGrid.setHalignment(child, HPos.CENTER);
        }
        for(int x = 1; x <= width; x++) {
            for(int y = 1; y <= height; y++) {
                Label child = new Label();
                Vector2D childPosition = new Vector2D(x-1+currentBounds.bottomLeftCorner().getX(),height-y+currentBounds.bottomLeftCorner().getY());
                Optional<WorldElement> element = map.objectAt(childPosition);
                element.ifPresent(worldElement -> child.setText(worldElement.toString()));
                mapGrid.add(child,x,y); // odwrócona orientacja przez wymogi zadania
                mapGrid.setHalignment(child, HPos.CENTER);
            }
        }
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
        List<MoveDirection> directions = OptionsParser.parse(Arrays.stream(infoText.getText().split(" ")).toList());
        GrassField grassMap = new GrassField(10);
        setWorldMap(grassMap);

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("mapgrid.fxml"));

        try {
            BorderPane root = loader.load();
            SimulationPresenter newPresenter = loader.getController();
            grassMap.addObserver(newPresenter);
            grassMap.addObserver((worldMap,message) -> {
                System.out.println(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss ").format(LocalDateTime.now()) + message);}
            );
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Simulation");
            stage.minWidthProperty().bind(root.minWidthProperty());
            stage.minHeightProperty().bind(root.minHeightProperty());

            stage.show();
            Thread thread = new Thread(new Simulation(directions, positions, grassMap));
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}
