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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SimulationPresenter implements MapChangeListener {
    private static final int CELL_HEIGHT = 40;
    private static final int CELL_WIDTH = 40;
    private Simulation simulation;
    private Animal followedAnimal;
    private WorldMap map;
    private PrintWriter csvWriter;
    private boolean saveToCSV = false;
    @FXML
    TextField width, height, initialPlants, plantEnergy, plantCount, animalsCount, initialEnergy, satisfactoryEnergy, requiredEnergy, minMutations, maxMutations, genomeLength, waitingTime;
    @FXML
    ComboBox<String> mutationVariant, mapVariant;
    @FXML
    GridPane mapGrid;
    @FXML
    Button pauseButton, preferredFields, mostCommonGenome;
    @FXML
    Label incorrectDataLabel, followedAnimalInfo, generalAnimalInfo;
    @FXML
    CheckBox saveLog;

    private void setWorldMap(WorldMap map) {
        this.map = map;
    }

    public void setSaveToCSV(boolean saveToCSV) {
        this.saveToCSV = saveToCSV;
        try {
            csvWriter = new PrintWriter(new File("log.csv"));
        } catch (IOException e) {
            System.out.println("Error while loading log.csv");
        }
    }

    public void setFollowedAnimal(Animal followedAnimal) {
        this.followedAnimal = followedAnimal;
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void drawMap() {
        preferredFields.setVisible(false);
        mostCommonGenome.setVisible(false);
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
                    Node child = new WorldElementBox(element,followedAnimal).getBox();
                    mapGrid.add(child,x,y);
                    GridPane.setHalignment(child,HPos.CENTER);
                }
            }
        }

        showStats();
    }

    public void showStats() {
        List<Vector2D> fields = new PositionGenerator(map.getCurrentBounds(),new HashMap<>()).getPositions();
        List<Vector2D> emptyFields = new ArrayList<>();
        for(Vector2D position : fields) {
            if(map.objectAt(position) == null) {
                emptyFields.add(position);
            }
        }

        List<String> allGenomes = map.getAnimals().stream()
                .map(Animal::getGenome)
                .toList();

        Map<String, Long> genomeCountMap = allGenomes.stream()
                .collect(Collectors.groupingBy(g -> g, Collectors.counting()));

        Optional<Map.Entry<String, Long>> mostCommonGenomeEntry = genomeCountMap.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue));

        int avgEnergy = 0, avgChildren = 0;
        for(Animal animal : map.getAnimals()) {
            avgEnergy += animal.getEnergy();
            avgChildren += animal.getChildrenNo();
        }
        avgEnergy /= map.getAnimals().size();
        avgChildren /= map.getAnimals().size();

        int avgLifespan = 0;
        for(Animal animal : map.getDeadAnimals()) {
            avgLifespan += animal.getAge();
        }
        if(!map.getDeadAnimals().isEmpty()) {
            avgLifespan /= map.getDeadAnimals().size();
        }

        generalAnimalInfo.setText("\tGeneral population statistics:\t\t" +
                "\nAmount of animals: \t\t" + map.getAnimals().size() +
                "\nAmount of plants: \t\t\t" + map.getPlants().size() +
                "\nAverage energy of animals: \t" + avgEnergy +
                "\nAverage amount of children: \t" + avgChildren +
                "\nMost common genome: \t\t" + mostCommonGenomeEntry.get().getKey() +
                "\nEmpty positions: \t\t\t" + emptyFields.size() +
                "\nAverage lifespan of the dead: \t" + avgLifespan + "\n\n\n");

        followedAnimalInfo.setText("\tFollowed animal statistics:\t\t\n" +
                (followedAnimal.isDead() ? "Died at day: \t\t" + followedAnimal.getDeathDay() : "Age: \t\t\t" + followedAnimal.getAge()) +
                "\nEnergy: \t\t\t" + followedAnimal.getEnergy() +
                "\nChildren: \t\t\t" + followedAnimal.getChildrenNo() +
                "\nDescendants: \t\t" + followedAnimal.getDescendantsNo() +
                "\nGenome: \t\t\t" + followedAnimal.getGenome() +
                "\nActivated gene: \t" + followedAnimal.getCurrentGene() +
                "\nPlants eaten: \t\t" + followedAnimal.getPlantsEaten() + '\n');

        if(saveToCSV) {
            csvWriter.println(String.join(",",Arrays.stream(generalAnimalInfo.getText().split("[\n\t]")).filter(s -> !s.isEmpty()).collect(Collectors.toList())));
            csvWriter.println(String.join(",",Arrays.stream(followedAnimalInfo.getText().split("[\n\t]")).filter(s -> !s.isEmpty()).collect(Collectors.toList())));
        }
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void mapChanged(WorldMap map, String message) {
        Platform.runLater(this::drawMap);
    }

    public void onSimulationPauseClicked(ActionEvent actionEvent) {
        if(pauseButton.getText().equals("Pause")) {
            pauseButton.setText("Resume");
            simulation.pause();

            for(Node node : mapGrid.getChildren()) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    int height = map.getCurrentBounds().upperRightCorner().getY()-map.getCurrentBounds().bottomLeftCorner().getY()+1;
                    int x = GridPane.getColumnIndex(node)-1;
                    int y = height-GridPane.getRowIndex(node);
                    Vector2D position = new Vector2D(x,y);
                    if(map.objectAt(position) instanceof Animal) {
                        followedAnimal = (Animal)(map.objectAt(position));
                    }
                    showStats();
                });
            }

            preferredFields.setVisible(true);
            mostCommonGenome.setVisible(true);
        } else {
            pauseButton.setText("Pause");
            preferredFields.setVisible(false);
            mostCommonGenome.setVisible(false);
            simulation.resume();
        }
    }


    public void onSaveConfigClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));

            printWriter.println(this.width.getText());
            printWriter.println(this.height.getText());
            printWriter.println(this.mapVariant.getValue());
            printWriter.println(this.initialPlants.getText());
            printWriter.println(this.plantEnergy.getText());
            printWriter.println(this.plantCount.getText());
            printWriter.println(this.animalsCount.getText());
            printWriter.println(this.initialEnergy.getText());
            printWriter.println(this.satisfactoryEnergy.getText());
            printWriter.println(this.requiredEnergy.getText());
            printWriter.println(this.minMutations.getText());
            printWriter.println(this.maxMutations.getText());
            printWriter.println(this.genomeLength.getText());
            printWriter.println(this.mutationVariant.getValue());
            printWriter.println(this.waitingTime.getText());
            printWriter.println(this.saveLog.isSelected());

            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error occured while loading the file");
        }
    }

    public void onPreferredFieldsClicked(ActionEvent actionEvent) {
        List<Vector2D> positions = new PositionGenerator(map.getPreferredBoundary(), new HashMap<>()).getPositions();
        List<Vector2D> tried = new ArrayList<>();

        for(Node node : mapGrid.getChildren()) {
            if(GridPane.getColumnIndex(node) == null) {
                continue;
            }
            int height = map.getCurrentBounds().upperRightCorner().getY()-map.getCurrentBounds().bottomLeftCorner().getY()+1;
            int x = GridPane.getColumnIndex(node)-1;
            int y = height-GridPane.getRowIndex(node);
            Vector2D position = new Vector2D(x,y);
            if(positions.contains(position)) {
                node.setStyle("-fx-background-color: lightgreen;");
                tried.add(position);
            }
        }

        for(Vector2D position : positions) {
            if(!tried.contains(position)) {
                Node child = new VBox();
                child.setStyle("-fx-background-color: lightgreen;");
                int height = map.getCurrentBounds().upperRightCorner().getY()-map.getCurrentBounds().bottomLeftCorner().getY()+1;
                int x = position.getX()+1;
                int y = height-position.getY();
                mapGrid.add(child,x,y);
            }
        }
    }

    public void onMostCommonGenomeClicked(ActionEvent actionEvent) {
        List<String> allGenomes = map.getAnimals().stream()
                .map(Animal::getGenome)
                .toList();

        Map<String, Long> genomeCountMap = allGenomes.stream()
                .collect(Collectors.groupingBy(g -> g, Collectors.counting()));

        Optional<Map.Entry<String, Long>> mostCommonGenomeEntry = genomeCountMap.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue));

        String mostCommonGenome = mostCommonGenomeEntry.get().getKey();

        List<Vector2D> fields = new PositionGenerator(map.getCurrentBounds(),new HashMap<>()).getPositions();

        for(Node node : mapGrid.getChildren()) {
            if(GridPane.getColumnIndex(node) == null) {
                continue;
            }
            int height = map.getCurrentBounds().upperRightCorner().getY()-map.getCurrentBounds().bottomLeftCorner().getY()+1;
            int x = GridPane.getColumnIndex(node)-1;
            int y = height-GridPane.getRowIndex(node);
            Vector2D position = new Vector2D(x,y);
            if(map.objectAt(position) instanceof Animal && ((Animal) map.objectAt(position)).getGenome().equals(mostCommonGenome)) {
                node.setStyle("-fx-background-color: lightblue;");
            }
        }
    }

    public void onLoadConfigClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            width.setText(reader.readLine());
            height.setText(reader.readLine());
            mapVariant.setValue(reader.readLine());
            initialPlants.setText(reader.readLine());
            plantEnergy.setText(reader.readLine());
            plantCount.setText(reader.readLine());
            animalsCount.setText(reader.readLine());
            initialEnergy.setText(reader.readLine());
            satisfactoryEnergy.setText(reader.readLine());
            requiredEnergy.setText(reader.readLine());
            minMutations.setText(reader.readLine());
            maxMutations.setText(reader.readLine());
            genomeLength.setText(reader.readLine());
            mutationVariant.setValue(reader.readLine());
            waitingTime.setText(reader.readLine());
            saveLog.setSelected(reader.readLine().equals("true"));

            reader.close();
        } catch (IOException e) {
            System.out.println("Error occured while loading the file");
        }
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
        Simulation newSimulation = new Simulation(newMap,plantCount,animalsCount,initialEnergy,genomeLength,waitingTime);

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("mapgrid.fxml"));

        try {
            GridPane root = loader.load();
            SimulationPresenter newPresenter = loader.getController();
            newPresenter.setWorldMap(newMap);
            newPresenter.setSimulation(newSimulation);
            newPresenter.setFollowedAnimal(newMap.getAnimals().get(0));
            newPresenter.setSaveToCSV(saveLog.isSelected());
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
