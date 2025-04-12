package com.alex678.entity;

import javafx.scene.image.Image;
import lombok.Data;

@Data
public abstract class Entity {
    private static int idCounter = 1;
    private final int id;
    protected Location location;
    protected Image image;

    public Entity(Location location) {
        id = idCounter;
        this.location = location;
        idCounter++;
        image = null;
    }

    public static void nullIdCounter() {
        idCounter = 1;
    }
}
