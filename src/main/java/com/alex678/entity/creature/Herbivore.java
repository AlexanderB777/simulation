package com.alex678.entity.creature;

import com.alex678.World;
import com.alex678.entity.Location;
import javafx.scene.image.Image;

public class Herbivore extends Creature {

    public Herbivore(Location location) {
        super(location);
        image = new Image(getClass().getClassLoader().getResourceAsStream("herbivore.png"));
    }

    @Override
    public Location makeMove(World world) {
        return location;
    }
}
