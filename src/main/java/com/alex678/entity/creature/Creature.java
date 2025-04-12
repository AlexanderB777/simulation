package com.alex678.entity.creature;

import com.alex678.World;
import com.alex678.entity.Entity;
import com.alex678.entity.Location;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class Creature extends Entity {
    private boolean alive;

    public abstract List<Class<? extends Entity>> getToAvoid();

    public Creature(Location location) {
        super(location);
        this.alive = true;
    }

    public abstract Location getNewLocation(World world);
}