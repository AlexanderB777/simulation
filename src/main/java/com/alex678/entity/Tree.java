package com.alex678.entity;

import javafx.scene.image.Image;

public class Tree extends Entity {
    public Tree(Location location) {
        super(location);
        image = new Image(getClass().getClassLoader().getResourceAsStream("tree.png"));
    }
}
