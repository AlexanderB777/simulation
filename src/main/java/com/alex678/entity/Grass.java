package com.alex678.entity;

import javafx.scene.image.Image;

public class Grass extends Entity {
    public Grass(Location location) {
        super(location);
        image = new Image(getClass().getClassLoader().getResourceAsStream("grass.png"));
    }
}
