package agh.ics.oop.model;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class WorldElementBox {
    VBox vbox;
    static final HashMap<String,Image> images = new HashMap<>();

    static {
        String path = "C:\\Users\\ASD\\Desktop\\Programowanie obiektowe\\PO_2023_PT0940_ZBOROWSKI\\oolab\\src\\main\\resources\\";
        try {
            images.put("grass.png",new Image(new FileInputStream(path + "grass.png")));
            images.put("up.png",new Image(new FileInputStream(path + "up.png")));
            images.put("down.png",new Image(new FileInputStream(path + "down.png")));
            images.put("right.png",new Image(new FileInputStream(path + "right.png")));
            images.put("left.png",new Image(new FileInputStream(path + "left.png")));
        } catch (FileNotFoundException e) {
            System.out.println("Could not load graphic files");
        }
    }

    public WorldElementBox(WorldElement element) {
        if(images.containsKey(element.getImageFile())) {
            Image image = images.get(element.getImageFile());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            Label label = new Label(element.getPosition().toString());
            vbox = new VBox();
            vbox.getChildren().add(imageView);
            vbox.getChildren().add(label);
            vbox.setAlignment(Pos.CENTER);
        }
    }

    public VBox getBox() {
        return vbox;
    }
}
