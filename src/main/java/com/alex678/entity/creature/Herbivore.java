package com.alex678.entity.creature;

import com.alex678.World;
import com.alex678.entity.*;
import javafx.scene.image.Image;

import java.util.List;

public final class Herbivore extends Creature implements Spawnable {

    public Herbivore(Location location) {
        super(location);
        image = new Image(getClass().getClassLoader().getResourceAsStream("herbivore.png"));
    }

    @Override
    public List<Class<? extends Entity>> getToAvoid() {
        return List.of(Herbivore.class, Predator.class, Tree.class, Rock.class);
    }

    @Override
    public Location getNewLocation(World world) {
        List<PathFinder.Point> path = PathFinder.findPath(world, location, Grass.class, getToAvoid());
        if (path != null && path.size() > 1) {
            return path.get(1).location();
        }
        return null;
    }
}
