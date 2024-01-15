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
    @FXML
    Label incorrectDataLabel;

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
        int width,height,initialPlants,plantEnergy,plantCount,animalsCount,initialEnergy,satisfactoryEnergy,requiredEnergy,minMutations,maxMutations,genomeLength,waitingTime;
        try {
             width = Integer.parseInt(this.width.getText());
             height = Integer.parseInt(this.height.getText());
             initialPlants = Integer.parseInt(this.initialPlants.getText());
             plantEnergy = Integer.parseInt(this.plantEnergy.getText());
             plantCount = Integer.parseInt(this.plantCount.getText());
             animalsCount = Integer.parseInt(this.animalsCount.getText());
             initialEnergy = Integer.parseInt(this.initialEnergy.getText());
             satisfactoryEnergy = Integer.parseInt(this.satisfactoryEnergy.getText());
             requiredEnergy = Integer.parseInt(this.requiredEnergy.getText());
             minMutations = Integer.parseInt(this.minMutations.getText());
             maxMutations = Integer.parseInt(this.maxMutations.getText());
             genomeLength = Integer.parseInt(this.genomeLength.getText());
             waitingTime = Integer.parseInt(this.waitingTime.getText());
        } catch (NumberFormatException e) {
            incorrectDataLabel.setText("Incorrect data");
            return;
        }

        if(width <= 0 || width > 1000 || height <= 0 || height > 1000
                || initialPlants < 0 || plantEnergy < 0 || plantCount < 0 || plantCount > width*height
                || animalsCount < 0 || animalsCount > width*height || initialEnergy <= 0 || satisfactoryEnergy <= 0
                || requiredEnergy <= 0 || minMutations < 0 || minMutations > maxMutations || maxMutations > genomeLength
                || genomeLength <= 0 || waitingTime <= 0) {
            incorrectDataLabel.setText("Incorrect data");
            return;
        }

        incorrectDataLabel.setText("");

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
