package com.alex678;

import com.alex678.entity.Entity;
import lombok.Getter;

@Getter
public class Game {
    private World world;

    public Game() {
        restart();
    }

    public void restart() {
        world = World.builder()
                .rocks(5)
                .grasses(20)
                .trees(7)
                .predators(3)
                .herbivores(5)
                .build();
        Entity.nullIdCounter();
    }
}