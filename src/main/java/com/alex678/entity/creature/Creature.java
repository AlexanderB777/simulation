package com.alex678.entity.creature;

import com.alex678.World;
import com.alex678.entity.Entity;
import com.alex678.entity.Location;

public abstract class Creature extends Entity {

    public Creature(Location location) {
        super(location);
    }

    public abstract Location makeMove(World world);
}