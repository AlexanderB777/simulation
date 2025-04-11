package com.alex678.entity.creature;

import com.alex678.World;
import com.alex678.entity.Entity;
import com.alex678.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

public class PathFinder {

    @AllArgsConstructor
    public static class Point {
        @Getter
        private Location location;
        private Point parent;
    }

    public static List<Point> findPath(World world, Location startLocation,
                                       Class<? extends Entity> toHunt, List<Class<? extends Entity>> toAvoid) {
        List<Location> visited = new ArrayList<>();
        Queue<Point> queue = new LinkedList<>();

        queue.add(new Point(startLocation, null));
        visited.add(startLocation);

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            Entity currentEntity = world.getEntity(current.location);

            if (currentEntity != null && toHunt.isAssignableFrom(currentEntity.getClass())
                    && !current.location.equals(startLocation)) {
                return buildPath(current);
            }

            for (int[] direction : World.DIRECTIONS) {
                Location newLocation = new Location(
                        current.location.row() + direction[0],
                        current.location.col() + direction[1]
                );

                if (isValidMove(world, visited, newLocation, toAvoid)) {
                    visited.add(newLocation);
                    queue.add(new Point(newLocation, current));
                }
            }
        }
        return null;
    }

    private static boolean isValidMove(World world, List<Location> visited,
                                       Location newLocation, List<Class<? extends Entity>> toAvoid) {
        if (newLocation.row() < 0
                || newLocation.row() >= world.getRows()
                || newLocation.col() < 0
                || newLocation.col() >= world.getColumns()) return false;
        if (visited.contains(newLocation)) return false;

        Optional<Entity> currentEntity = Optional.ofNullable(world.getEntity(newLocation));

        return currentEntity.isEmpty() || !(toAvoid.contains(currentEntity.get().getClass()));
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