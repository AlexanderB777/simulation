package com.alex678;

import com.alex678.entity.Entity;
import com.alex678.entity.Location;
import com.alex678.entity.creature.Creature;
import lombok.Getter;

import java.util.List;

public class Game {
    @Getter
    private World world;

    public Game() {
        restart();
    }

    public void restart() {
        world = World.builder()
                .rocks(5)
                .grasses(20)
                .trees(7)
                .build();
        Entity.nullIdCounter();
    }

    public Entity getCell(Location location) {
        return world.getEntity(location);
    }

    public List<Creature> getAllCreatures() {
        return world.getCreatures();
    }
}