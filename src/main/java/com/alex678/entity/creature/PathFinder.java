package com.alex678.entity.creature;

import com.alex678.World;
import com.alex678.entity.Entity;
import com.alex678.entity.Location;

import java.util.*;

public class PathFinder {

    public record Point(Location location, Point parent) {}

    public static List<Point> findPath(World world, Location startLocation,
                                       Class<? extends Entity> toHunt,
                                       List<Class<? extends Entity>> toAvoid) {
        List<Location> visited = new ArrayList<>();
        Queue<Point> queue = new LinkedList<>();

        queue.add(new Point(startLocation, null));
        visited.add(startLocation);

        while (!queue.isEmpty()) {
            Point currentPoint = queue.poll();
            Entity currentEntity = world.getEntity(currentPoint.location);

            if (currentEntity != null
                    && currentEntity.getClass() == toHunt
                    && !currentPoint.location.equals(startLocation)) {
                return buildPath(currentPoint);
            }

            for (int[] direction : World.DIRECTIONS) {
                Location newLocation = new Location(
                        currentPoint.location.row() + direction[0],
                        currentPoint.location.col() + direction[1]
                );

                if (isValidMove(world, visited, newLocation, toAvoid)) {
                    visited.add(newLocation);
                    queue.add(new Point(newLocation, currentPoint));
                }

            }
        }
        return null;
    }

    private static boolean isValidMove(World world,
                                       List<Location> visited,
                                       Location newLocation,
                                       List<Class<? extends Entity>> toAvoid) {
        if (newLocation.row() < 0
                || newLocation.row() >= world.getRows()
                || newLocation.col() < 0
                || newLocation.col() >= world.getColumns()
                || visited.contains(newLocation)) return false;

        Optional<Entity> newLocationEntity = Optional.ofNullable(world.getEntity(newLocation));

        return newLocationEntity.isEmpty() || !(toAvoid.contains(newLocationEntity.get().getClass()));
    }

    private static List<Point> buildPath(Point end) {
        List<Point> path = new ArrayList<>();
        for (Point p = end; p != null; p = p.parent) {
            path.add(p);
        }
        Collections.reverse(path);
        return path;
    }
}