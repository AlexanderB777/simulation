package com.alex678;

import com.alex678.entity.*;
import com.alex678.entity.creature.Creature;
import com.alex678.entity.creature.Herbivore;
import com.alex678.entity.creature.Predator;
import lombok.Getter;

import java.util.*;

public class World {
    Random random = new Random();
    private final Map<Location, Entity> entitiesMap;
    @Getter
    private final List<Creature> creatures;
    @Getter
    private final int rows;
    @Getter
    private final int columns;
    public static final int[][] DIRECTIONS = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static WorldBuilder builder() {
        return new WorldBuilder();
    }

    public World(int rows,
                 int columns,
                 int rocks,
                 int trees,
                 int grasses,
                 int predators,
                 int herbivores) {
        this.rows = rows;
        this.columns = columns;
        this.entitiesMap = new HashMap<>();
        this.creatures = new ArrayList<>();
        fillWithEntities(rocks, trees, grasses, predators, herbivores);
    }

    public void moveCreature(Creature creature, Location newLocation) {
        Location currentLocation = creature.getLocation();
        creature.setLocation(newLocation);
        entitiesMap.remove(currentLocation);
        entitiesMap.put(newLocation, creature);
    }

    public List<Entity> getEntities() {
        return new ArrayList<>(entitiesMap.values());
    }

    private void fillWithEntities(int rocks,
                                  int trees,
                                  int grasses,
                                  int predators,
                                  int herbivores) {
        fillWithRocks(rocks);
        fillWithTrees(trees);
        fillWithGrasses(grasses);
        fillWithPredators(predators);
        fillWithHerbivores(herbivores);
    }

    private boolean isNearFreeCell(Location location) {
        for (int[] direction : DIRECTIONS) {
            Location nearLocation =
                    new Location(location.getRow() + direction[0],
                            location.getCol() + direction[1]);
            if (entitiesMap.containsKey(nearLocation)) return false;
        }
        return true;
    }

    private Location getRandomLocation() {
        int row = random.nextInt(rows) + 1;
        int col = random.nextInt(columns) + 1;
        return new Location(row, col);
    }

    public Entity getEntity(Location location) {
        return entitiesMap.get(location);
    }

    public static class WorldBuilder {
        private int rows = 10;
        private int columns = 25;
        private int rocks = 10;
        private int trees = 10;
        private int grasses = 10;
        private int predators = 3;
        private int herbivores = 5;

        public WorldBuilder rows(int rows) {
            this.rows = rows;
            return this;
        }

        public WorldBuilder columns(int columns) {
            this.columns = columns;
            return this;
        }

        public WorldBuilder rocks(int rocks) {
            this.rocks = rocks;
            return this;
        }

        public WorldBuilder trees(int trees) {
            this.trees = trees;
            return this;
        }

        public WorldBuilder grasses(int grasses) {
            this.grasses = grasses;
            return this;
        }

        public WorldBuilder predators(int predators) {
            this.predators = predators;
            return this;
        }

        public WorldBuilder herbivores(int herbivores) {
            this.herbivores = herbivores;
            return this;
        }

        public World build() {
            return new World(rows, columns, rocks, trees, grasses, predators, herbivores);
        }
    }

    private void fillWithRocks(int rocks) {
        while (rocks > 0) {
            Location location = getRandomLocation();
            if (!entitiesMap.containsKey(location) && isNearFreeCell(location)) {
                Rock rock = new Rock(location);
                entitiesMap.put(location, rock);
                rocks--;
            }
        }
    }

    private void fillWithTrees(int trees) {
        while (trees > 0) {
            Location location = getRandomLocation();
            if (!entitiesMap.containsKey(location) && isNearFreeCell(location)) {
                Tree tree = new Tree(location);
                entitiesMap.put(location, tree);
                trees--;
            }
        }
    }

    private void fillWithGrasses(int grasses) {
        while (grasses > 0) {
            Location location = getRandomLocation();
            if (!entitiesMap.containsKey(location) && isNearFreeCell(location)) {
                Grass grass = new Grass(location);
                entitiesMap.put(location, grass);
                grasses--;
            }
        }
    }

    private void fillWithPredators(int predators) {
        while (predators > 0) {
            Location location = getRandomLocation();
            if (!entitiesMap.containsKey(location) && isNearFreeCell(location)) {
                Predator predator = new Predator(location);
                entitiesMap.put(location, predator);
                creatures.add(predator);
                predators--;
            }
        }
    }

    private void fillWithHerbivores(int herbivores) {
        while (herbivores > 0) {
            Location location = getRandomLocation();
            if (!entitiesMap.containsKey(location) && isNearFreeCell(location)) {
                Herbivore herbivore = new Herbivore(location);
                entitiesMap.put(location, herbivore);
                creatures.add(herbivore);
                herbivores--;
            }
        }
    }
}