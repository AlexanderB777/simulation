package com.alex678.entity;

import javafx.scene.image.Image;

import java.util.*;

import static java.lang.Math.abs;

public class Predator extends Creature {

    public Predator(int row, int col) {
        super(row, col);
        image = new Image(getClass().getClassLoader().getResourceAsStream("predator.png"));
    }

    @Override
    public void makeMove(Entity[][] field) {
        Herbivore nearestHerbivore = findNearestHerbivore(field);
        int[] nextField = getDirection();
    }

    private int[] getDirection() {
        int[] nextField = new int[2];




        return nextField;
    }

    private Herbivore findNearestHerbivore(Entity[][] field) {
        List<Herbivore> herbivores = new LinkedList<>();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 25; col++) {
                if (field[row][col].getClass() == Herbivore.class) {
                    herbivores.add((Herbivore) field[row][col]);
                }
            }
        }
        return herbivores.stream()
                .min(Comparator.comparing(herbivore -> getDistance(this, herbivore)))
                .orElse(null);
    }

    private int getDistance(Entity entity1, Entity entity2) {
        return abs(entity1.row - entity2.row) + abs(entity1.col - entity2.col);
    }

}
