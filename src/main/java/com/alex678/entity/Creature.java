package com.alex678.entity;

public abstract class Creature extends Entity {
//    private int speed;
    private int health;

    public Creature(int row, int col) {
        super(row, col);
        health = 3;
    }

    public abstract void makeMove(Entity[][] field);

    public void decrementHealth() {
        health--;
    }

}
