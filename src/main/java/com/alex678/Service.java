package com.alex678;

import com.alex678.entity.Entity;
import com.alex678.entity.Location;
import com.alex678.entity.creature.Creature;
import com.alex678.entity.creature.Herbivore;
import com.alex678.entity.creature.Spawnable;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
public class Service {
    Random rand = new Random();
    private final Game game;
    private final World world;
    private final Map<Location, Entity> entityesMap;
    private final List<Location> spawnableLocations;
    private final static int[][] DIRECTIONS = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public Service(Game game, World world) {
        this.game = game;
        this.world = world;
        entityesMap = world.getEntitiesMap();
        spawnableLocations = getSpawnableLocations(world);
    }

    public ServiceMakeMoveReport makeMove(Creature creature, Location newLocation) {
        log.info("Вызван метод: makeMove(Creature creature, Location newLocation) )");
        log.info("У сущности: {}", creature.getLocation());
        log.info("На локацию: {}", newLocation);
        Location oldLocation = creature.getLocation();

        if (newLocation == null) {
            return ServiceMakeMoveReport.builder().entity(creature).oldLocation(oldLocation).build();
        }
        Optional<Entity> optionalNewLocationEntity = Optional.ofNullable(world.getEntity(newLocation));
        if (optionalNewLocationEntity.isEmpty()) {
            moveCreatureOnFreeCell(creature, newLocation);
            return ServiceMakeMoveReport
                    .builder()
                    .entity(creature)
                    .oldLocation(oldLocation)
                    .newLocation(newLocation)
                    .isMoveMade(true)
                    .build();
        }
        Entity newLocationEntity = optionalNewLocationEntity.get();
        if (creature.getToAvoid().contains(newLocationEntity.getClass())) {
            return ServiceMakeMoveReport.builder().entity(creature).oldLocation(oldLocation).build();
        }
        if (newLocationEntity instanceof Spawnable) {
            world.removeEntity(newLocationEntity);
            if (newLocationEntity instanceof Herbivore herbivore) {
                herbivore.setAlive(false);
            }
            moveCreatureOnFreeCell(creature, newLocation);
            Entity spawnedEntity = spawnEntity(newLocationEntity.getClass());
            return ServiceMakeMoveReport
                    .builder()
                    .entity(creature)
                    .oldLocation(oldLocation)
                    .newLocation(newLocation)
                    .killedEntity(newLocationEntity)
                    .spawnedEntity(spawnedEntity)
                    .isMoveMade(true).build();
        }
        throw new RuntimeException("Creature is not spawnable and not avoidable");
    }

    private void updateSpawnableLocations(Entity entity) {
        Location location = entity.getLocation();
        List<Location> neighbours = getNeighbours(location);
        spawnableLocations.remove(location);
        spawnableLocations.removeAll(neighbours);
    }

    private void updateSpawnableLocations(Location oldLocation, Location newLocation) {
        List<Location> neighbours = getNeighbours(oldLocation);
        neighbours.remove(newLocation);
        for (Location neighbour : neighbours) {
            if (isLocationValidForSpawn(neighbour)) {
                spawnableLocations.add(neighbour);
            }
        }
        spawnableLocations.removeAll(getNeighbours(newLocation));
    }

    private List<Location> getSpawnableLocations(World world) {
        List<Location> spawnableLocations = new ArrayList<>();
        for (int i = 0; i < world.getRows(); i++) {
            for (int j = 0; j < world.getColumns(); j++) {
                Location location = new Location(i, j);
                if (isLocationValidForSpawn(location)) {
                    spawnableLocations.add(location);
                }
            }
        }
        return spawnableLocations;
    }

    private List<Location> getNeighbours(Location location) {
        List<Location> neighbours = new ArrayList<>();
        for (int[] direction : DIRECTIONS) {
            Location neighbour = new Location(location.row() + direction[0], location.col() + direction[1]);
            if (isValidLocation(neighbour)) {
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    private boolean isValidLocation(Location location) {
        return (location.row() >= 0 && location.row() < world.getRows()
                && location.col() >= 0 && location.col() < world.getColumns());
    }

    private Entity spawnEntity(Class<? extends Entity> aClass) {
        Location location = getLocationForSpawn();
        try {
            Constructor<? extends Entity> constructor = aClass.getConstructor(Location.class);
            Entity newEntity = constructor.newInstance(location);
            world.putEntity(newEntity);
            updateSpawnableLocations(newEntity);
            return newEntity;
        } catch (NoSuchMethodException
                 | IllegalAccessException
                 | InstantiationException
                 | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Location getLocationForSpawn() {
        int randomIndex = rand.nextInt(spawnableLocations.size());
        return spawnableLocations.get(randomIndex);
    }

    private boolean isLocationValidForSpawn(Location location) {
        if (entityesMap.containsKey(location)) {
            return false;
        }
        for (int[] direction : DIRECTIONS) {
            Location nearLocation = new Location(location.row() + direction[0],
                    location.col() + direction[1]);
            if (entityesMap.containsKey(nearLocation)) {
                return false;
            }
        }
        return true;
    }

    private void moveCreatureOnFreeCell(Creature creature, Location newLocation) {
        Location oldLocation = creature.getLocation();
        world.removeEntity(creature);
        creature.setLocation(newLocation);
        world.putEntity(creature);
        updateSpawnableLocations(oldLocation, newLocation);
    }
}