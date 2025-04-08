package com.alex678.entity;

import javafx.scene.image.Image;

public class Tree extends Entity {
    public Tree(int row, int col) {
        super(row, col);
        image = new Image(getClass().getClassLoader().getResourceAsStream("tree.png"));
    }
}
