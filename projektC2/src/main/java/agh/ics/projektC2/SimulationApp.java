package agh.ics.projektC2;

import agh.ics.projektC2.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SimulationApp extends Application {

    public void start(Stage primaryStage) throws Exception { // ?
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        GridPane viewRoot = loader.load();
        SimulationPresenter newPresenter = loader.getController();
       // grassMap.addObserver(newPresenter);
        configureState(primaryStage,viewRoot);
        primaryStage.show();
    }

    private void configureState(Stage primaryStage, GridPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Darwin World");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
