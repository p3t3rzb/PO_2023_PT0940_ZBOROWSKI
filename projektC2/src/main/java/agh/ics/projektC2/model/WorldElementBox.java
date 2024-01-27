package agh.ics.projektC2.model;

import javafx.geometry.Pos;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class WorldElementBox {
    VBox vbox;
    private static final HashMap<String, Image> images = new HashMap<>();

    static {
            images.put("water.png",new Image("water.png"));
            images.put("plant.png",new Image("plant.png"));
            images.put("up.png",new Image("up.png"));
            images.put("down.png",new Image("down.png"));
            images.put("right.png",new Image("right.png"));
            images.put("left.png",new Image("left.png"));
            images.put("upright.png",new Image("upright.png"));
            images.put("downleft.png",new Image("downleft.png"));
            images.put("rightdown.png",new Image("rightdown.png"));
            images.put("upleft.png",new Image("upleft.png"));
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