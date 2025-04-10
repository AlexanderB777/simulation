package com.alex678.entity.creature;

import com.alex678.World;
import com.alex678.entity.Location;
import javafx.scene.image.Image;

import java.util.List;

public class Predator extends Creature {

    public Predator(Location location) {
        super(location);
        image = new Image(getClass().getClassLoader().getResourceAsStream("predator.png"));
    }

    @Override
    public Location makeMove(World world) {
        List<PathFinder.Point> path = PathFinder.findPath(world, location);
        if (path != null && path.size() > 1) {
            PathFinder.Point next = path.get(1);
            return path.get(1).getLocation();
        }
        return null;
    }
}
