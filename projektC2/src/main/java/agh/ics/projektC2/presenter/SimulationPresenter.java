package agh.ics.projektC2.presenter;

import agh.ics.projektC2.Simulation;
import agh.ics.projektC2.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationPresenter implements MapChangeListener {
    private static final int CELL_HEIGHT = 40;
    private static final int CELL_WIDTH = 40;
    private Simulation simulation;
    private WorldMap map;
    @FXML
    TextField width, height, initialPlants, plantEnergy, plantCount, animalsCount, initialEnergy, satisfactoryEnergy, requiredEnergy, minMutations, maxMutations, genomeLength, waitingTime;
    @FXML
    ComboBox<String> mutationVariant, mapVariant;
    @FXML
    GridPane mapGrid;
    @FXML
    Button pauseButton;

    private void setWorldMap(WorldMap map) {
        this.map = map;
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
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
        GridPane.setHalignment(xyLabel, HPos.CENTER);
        for(int x = 1; x <= width; x++) {
            Label child = new Label();
            child.setText(String.valueOf(x-1+currentBounds.bottomLeftCorner().getX()));
            mapGrid.add(child,x,0);
            GridPane.setHalignment(child, HPos.CENTER);
        }
        for(int y = 1; y <= height; y++) {
            Label child = new Label();
            child.setText(String.valueOf(height-y+currentBounds.bottomLeftCorner().getY())); // odwrócona orientacja przez wymogi zadania
            mapGrid.add(child,0,y);
            GridPane.setHalignment(child, HPos.CENTER);
        }
        for(int x = 1; x <= width; x++) {
            for(int y = 1; y <= height; y++) {
                Vector2D childPosition = new Vector2D(x-1+currentBounds.bottomLeftCorner().getX(),height-y+currentBounds.bottomLeftCorner().getY());
                WorldElement element = map.objectAt(childPosition);
                if(element != null) {
                    Node child = new WorldElementBox(element).getBox();
                    mapGrid.add(child,x,y);
                    GridPane.setHalignment(child,HPos.CENTER);
                }
            }
        }
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void mapChanged(WorldMap map, String message) {
        Platform.runLater(() -> {
            drawMap(map);
        });
    }

    public void onSimulationPauseClicked(ActionEvent actionEvent) {
        //pauseButton.setText(pauseButton.getText().equals("Pause") ? "Resume" : "Pause");
        if(pauseButton.getText().equals("Pause")) {
            pauseButton.setText("Resume");
            simulation.pause();
        } else {
            pauseButton.setText("Pause");
            simulation.resume();
        }
        //pauseButton.getText().equals("Pause") ? simulation.pause() : simulation.resume();
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) {
        int width = Integer.parseInt(this.width.getText());
        int height = Integer.parseInt(this.height.getText());
        int initialPlants = Integer.parseInt(this.initialPlants.getText());
        int plantEnergy = Integer.parseInt(this.plantEnergy.getText());
        int plantCount = Integer.parseInt(this.plantCount.getText());
        int animalsCount = Integer.parseInt(this.animalsCount.getText());
        int initialEnergy = Integer.parseInt(this.initialEnergy.getText());
        int satisfactoryEnergy = Integer.parseInt(this.satisfactoryEnergy.getText());
        int requiredEnergy = Integer.parseInt(this.requiredEnergy.getText());
        int minMutations = Integer.parseInt(this.minMutations.getText());
        int maxMutations = Integer.parseInt(this.maxMutations.getText());
        int genomeLength = Integer.parseInt(this.genomeLength.getText());
        int waitingTime = Integer.parseInt(this.waitingTime.getText());

        Mutation mutation = mutationVariant.getValue().equals("Fully random") ? new FullyRandomMutation() : new SwapMutation();
        WorldMap newMap = mapVariant.getValue().equals("Earth") ? new EarthMap(width,height,plantEnergy,satisfactoryEnergy,requiredEnergy,mutation,minMutations,maxMutations,initialPlants)
                                                           : new FloodingMap(width,height,plantEnergy,satisfactoryEnergy,requiredEnergy,mutation,minMutations,maxMutations,initialPlants);
        //newMap.addObserver(new ConsoleMapDisplay());
        Simulation newSimulation = new Simulation(newMap,plantCount,animalsCount,initialEnergy,genomeLength,waitingTime);

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("mapgrid.fxml"));

        try {
            VBox root = loader.load();
            SimulationPresenter newPresenter = loader.getController();
            newPresenter.setSimulation(newSimulation);
            newMap.addObserver(newPresenter);
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Simulation");
            stage.minWidthProperty().bind(root.minWidthProperty());
            stage.minHeightProperty().bind(root.minHeightProperty());

            stage.show();
            Thread thread = new Thread(newSimulation);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
