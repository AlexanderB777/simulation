package com.alex678.entity;

import javafx.scene.image.Image;

public class Rock extends Entity {
    public Rock(Location location) {
        super(location);
        image = new Image(getClass().getClassLoader().getResourceAsStream("rock.png"));
    }
}
