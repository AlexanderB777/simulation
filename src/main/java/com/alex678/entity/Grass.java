package com.alex678.entity;

import com.alex678.entity.creature.Spawnable;
import javafx.scene.image.Image;

public final class Grass extends Entity implements Spawnable {
    public Grass(Location location) {
        super(location);
        image = new Image(getClass().getClassLoader().getResourceAsStream("grass.png"));
    }
}
