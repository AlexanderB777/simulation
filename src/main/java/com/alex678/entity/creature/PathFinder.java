package com.alex678.entity.creature;

import com.alex678.World;
import com.alex678.entity.Entity;
import com.alex678.entity.Location;
import com.alex678.entity.Rock;
import com.alex678.entity.Tree;
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

    public static List<Point> findPath(World world, Location startLocation) {
        List<Location> visited = new ArrayList<>();
        Queue<Point> queue = new LinkedList<>();

        queue.add(new Point(startLocation, null));
        visited.add(startLocation);

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            Entity currentEntity = world.getEntity(current.location);

            if (currentEntity instanceof Herbivore
                    && !(current.location.getRow() == startLocation.getRow()
                    && current.location.getCol() == startLocation.getCol())) {
                return buildPath(current);
            }

            for (int[] direction : World.DIRECTIONS) {
                Location newLocation = new Location(
                        current.location.getRow() + direction[0],
                        current.location.getCol() + direction[1]
                );

                if (isValidMove(world, visited, newLocation)) {
                    visited.add(newLocation);
                    queue.add(new Point(newLocation, current));
                }
            }
        }
        return null;
    }

    private static boolean isValidMove(World world, List<Location> visited, Location location) {
        if (location.getRow() < 0
                || location.getRow() >= world.getRows()
                || location.getCol() < 0
                || location.getCol() >= world.getColumns()) return false;
        if (visited.contains(location)) return false;

        Entity currentEntity = world.getEntity(location);
        return !(currentEntity instanceof Tree || currentEntity instanceof Rock);
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