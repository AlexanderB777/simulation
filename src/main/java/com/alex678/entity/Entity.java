package com.alex678.entity;

import javafx.scene.image.Image;

public abstract class Entity {
    private static int idCounter = 1;
    private final int id;
    protected int row;
    protected int col;
    protected Image image;

    public Entity(int row, int col) {
        this.id = idCounter;
        this.row = row;
        this.col = col;
        idCounter++;
        image = null;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public static void nullIdCounter() {
        idCounter = 1;
    }

    public Image getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
