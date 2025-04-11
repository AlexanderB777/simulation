package com.alex678;

import com.alex678.entity.*;
import com.alex678.entity.creature.Creature;
import com.alex678.entity.creature.Herbivore;
import com.alex678.entity.creature.Predator;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class World {
    private final Map<Location, Entity> entitiesMap;
    private final List<Creature> creatures;
    private final int rows;
    private final int columns;
    public static final int[][] DIRECTIONS = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static WorldBuilder builder() {
        return new WorldBuilder();
    }

    public World(int rows, int columns, Map<Location, Entity> entitiesMap) {
        this.rows = rows;
        this.columns = columns;
        this.entitiesMap = entitiesMap;
        this.creatures = entitiesMap.values()
                .stream()
                .filter(Creature.class::isInstance)
                .map(Creature.class::cast)
                .collect(Collectors.toList());
    }

    public void removeEntity(Entity entity) {
        entitiesMap.remove(entity.getLocation());
        if (entity instanceof Creature) {
            creatures.remove((Creature) entity);
        }
    }

    public void putEntity(Entity entity) {
        entitiesMap.put(entity.getLocation(), entity);
        if (entity instanceof Creature) {
            creatures.add((Creature) entity);
        }
    }

    public Entity getEntity(Location location) {
        return entitiesMap.get(location);
    }

    public List<Entity> getEntities() {
        return new ArrayList<>(entitiesMap.values());
    }

    public static class WorldBuilder {
        Random random = new Random();
        private int rows = 10;
        private int columns = 25;
        private int rocks = 10;
        private int trees = 10;
        private int grasses = 10;
        private int predators = 3;
        private int herbivores = 5;
        private Map<Location, Entity> entitiesMap = new HashMap<>();

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
            fillWithEntities(rocks, trees, grasses, predators, herbivores);
            return new World(rows, columns, entitiesMap);
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

        private void fillWithRocks(int rocks) {
            while (rocks > 0) {
                Location location = getRandomLocation();
                if (isNearFreeCell(location)) {
                    Rock rock = new Rock(location);
                    entitiesMap.put(location, rock);
                    rocks--;
                }
            }
        }

        private void fillWithTrees(int trees) {
            while (trees > 0) {
                Location location = getRandomLocation();
                if (isNearFreeCell(location)) {
                    Tree tree = new Tree(location);
                    entitiesMap.put(location, tree);
                    trees--;
                }
            }
        }

        private void fillWithGrasses(int grasses) {
            while (grasses > 0) {
                Location location = getRandomLocation();
                if (isNearFreeCell(location)) {
                    Grass grass = new Grass(location);
                    entitiesMap.put(location, grass);
                    grasses--;
                }
            }
        }

        private void fillWithPredators(int predators) {
            while (predators > 0) {
                Location location = getRandomLocation();
                if (isNearFreeCell(location)) {
                    Predator predator = new Predator(location);
                    entitiesMap.put(location, predator);
                    predators--;
                }
            }
        }

        private void fillWithHerbivores(int herbivores) {
            while (herbivores > 0) {
                Location location = getRandomLocation();
                if (isNearFreeCell(location)) {
                    Herbivore herbivore = new Herbivore(location);
                    entitiesMap.put(location, herbivore);
                    herbivores--;
                }
            }
        }

        private Location getRandomLocation() {
            while (true) {
                int row = random.nextInt(rows);
                int col = random.nextInt(columns);
                Location location = new Location(row, col);
                if (!entitiesMap.containsKey(location)) return location;
            }
        }

        private boolean isNearFreeCell(Location location) {
            for (int[] direction : DIRECTIONS) {
                Location nearLocation =
                        new Location(location.row() + direction[0],
                                location.col() + direction[1]);
                if (entitiesMap.containsKey(nearLocation)) return false;
            }
            return true;
        }

    }
}