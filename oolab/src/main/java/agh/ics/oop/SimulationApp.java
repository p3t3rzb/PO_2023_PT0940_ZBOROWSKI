package agh.ics.oop;

import agh.ics.oop.model.ConsoleMapDisplay;
import agh.ics.oop.model.GrassField;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SimulationApp extends Application {
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
        configureState(primaryStage,viewRoot);

        List<Vector2D> positions = List.of(new Vector2D(2,2), new Vector2D(3,4));
        List<MoveDirection> directions = OptionsParser.parse(getParameters().getRaw());
        GrassField grassMap = new GrassField(10);
        grassMap.addObserver(new SimulationPresenter());
        grassMap.addObserver(new ConsoleMapDisplay());
        new Simulation(directions, positions, grassMap).run();
        SimulationPresenter presenter1 = new SimulationPresenter();
        presenter.setWorldMap(grassMap);

        primaryStage.show();
    }

    private void configureState(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
