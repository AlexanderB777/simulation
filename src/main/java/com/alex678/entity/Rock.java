package com.alex678.entity;

import javafx.scene.image.Image;

public class Rock extends Entity {
    public Rock(int row, int col) {
        super(row, col);
        image = new Image(getClass().getClassLoader().getResourceAsStream("rock.png"));
    }
}
