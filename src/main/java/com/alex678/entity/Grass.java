package com.alex678.entity;

import javafx.scene.image.Image;

public class Grass extends Entity {
    public Grass(int row, int col) {
        super(row, col);
        image = new Image(getClass().getClassLoader().getResourceAsStream("grass.png"));
    }
}
