package com.alex678.entity.creature;

import com.alex678.World;
import com.alex678.entity.Entity;
import com.alex678.entity.Location;
import com.alex678.entity.Rock;
import com.alex678.entity.Tree;
import javafx.scene.image.Image;

import java.util.List;

public final class Predator extends Creature {

    @Override
    public List<Class<? extends Entity>> getToAvoid() {
        return List.of(Predator.class, Tree.class, Rock.class);
    }

    public Predator(Location location) {
        super(location);
        image = new Image(getClass().getClassLoader().getResourceAsStream("predator.png"));
    }

    @Override
    public Location getNewLocation(World world) {
        List<PathFinder.Point> path = PathFinder.findPath(world, location, Herbivore.class, getToAvoid());
        if (path != null && path.size() > 1) {
            return path.get(1).getLocation();
        }
        return null;
    }
}
