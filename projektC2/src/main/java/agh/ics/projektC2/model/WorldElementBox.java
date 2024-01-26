package agh.ics.projektC2.model; // to na pewno model?

import javafx.geometry.Pos;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class WorldElementBox {
    VBox vbox;
    static final HashMap<String, Image> images = new HashMap<>();

    static {
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\";
        try {
            images.put("water.png",new Image(new FileInputStream(path + "water.png")));
            images.put("plant.png",new Image(new FileInputStream(path + "plant.png")));
            images.put("up.png",new Image(new FileInputStream(path + "up.png")));
            images.put("down.png",new Image(new FileInputStream(path + "down.png")));
            images.put("right.png",new Image(new FileInputStream(path + "right.png")));
            images.put("left.png",new Image(new FileInputStream(path + "left.png")));
            images.put("upright.png",new Image(new FileInputStream(path + "upright.png")));
            images.put("downleft.png",new Image(new FileInputStream(path + "downleft.png")));
            images.put("rightdown.png",new Image(new FileInputStream(path + "rightdown.png")));
            images.put("upleft.png",new Image(new FileInputStream(path + "upleft.png")));
        } catch (FileNotFoundException e) {
            System.out.println("Could not load graphic files");
        }
    }

    public WorldElementBox(WorldElement element, WorldElement special) {
        if(images.containsKey(element.getImageFile())) {
            Image image = images.get(element.getImageFile());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);

            if(element instanceof Animal) {
                ColorAdjust colorAdjust = new ColorAdjust();
                if(element == special) {
                    colorAdjust.setHue(5);
                    colorAdjust.setBrightness(-0.3);
                } else {
                    int energy = ((Animal) element).getEnergy();
                    colorAdjust.setBrightness(0.5 - energy / 200.0);
                }
                imageView.setEffect(colorAdjust);
            }

            vbox = new VBox();
            vbox.getChildren().add(imageView);
            //vbox.getChildren().add(label);
            vbox.setAlignment(Pos.CENTER);
        }
    }

    public VBox getBox() {
        return vbox;
    }
}