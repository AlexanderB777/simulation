package com.alex678.entity;

import javafx.scene.image.Image;

public class Herbivore extends Creature {
    private int grassesToLife;

    public Herbivore(int row, int col) {
        super(row, col);
        image = new Image(getClass().getClassLoader().getResourceAsStream("herbivore.png"));
        grassesToLife = 5;
    }

    @Override
    public void makeMove(Entity[][] field) {

    }
}
