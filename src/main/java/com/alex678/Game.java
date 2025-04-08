package com.alex678;

import com.alex678.entity.*;
import javafx.scene.layout.StackPane;

import java.util.Arrays;
import java.util.Random;

public class Game {
    Random random = new Random();
    private Entity[][] field = new Entity[10][25];

    public Game() {
        reset();
    }

    public void reset() {
        for (Entity[] entities : field) {
            Arrays.fill(entities, null);
        }
        Entity.nullIdCounter();
        setStaticObjectsRandom();
        setCreaturesRandom();
    }

    private void setStaticObjectsRandom() {
        int rocks = 20;
        int trees = 20;
        int grass = 40;
        while (rocks > 0) {
            int row = random.nextInt(10);
            int col = random.nextInt(25);
            if (field[row][col] == null) {
                field[row][col] = new Rock(row, col);
            }
            rocks--;
        }
        while (trees > 0) {
            int row = random.nextInt(10);
            int col = random.nextInt(25);
            if (field[row][col] == null) {
                field[row][col] = new Tree(row, col);
            }
            trees--;
        }
        while (grass > 0) {
            int row = random.nextInt(10);
            int col = random.nextInt(25);
            if (field[row][col] == null) {
                field[row][col] = new Grass(row, col);
            }
            grass--;
        }
    }

    private void setCreaturesRandom() {
        int herbivores = 5;
        int predators = 5;
        while (herbivores > 0) {
            int row = random.nextInt(10);
            int col = random.nextInt(25);
            if (field[row][col] == null) {
                field[row][col] = new Herbivore(row, col);
            }
            herbivores--;
        }
        while (predators > 0) {
            int row = random.nextInt(10);
            int col = random.nextInt(25);
            if (field[row][col] == null) {
                field[row][col] = new Predator(row, col);
            }
            predators--;
        }
    }

    private boolean isFreeCell(int row, int col) {
        return field[row][col] == null;
    }

    public Entity getCell(int row, int col) {
        return field[row][col];
    }

}
