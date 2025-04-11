package com.alex678.entity.creature;

import com.alex678.World;
import com.alex678.entity.Entity;
import com.alex678.entity.Location;

import java.util.List;

public abstract class Creature extends Entity {
    public abstract List<Class<? extends Entity>> getToAvoid();

    public Creature(Location location) {
        super(location);
    }

    public abstract Location getNewLocation(World world);
}