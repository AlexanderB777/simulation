package com.alex678;

import com.alex678.entity.Entity;
import com.alex678.entity.Location;
import com.alex678.entity.Rock;
import com.alex678.entity.Tree;
import com.alex678.entity.creature.Creature;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameController {
    private final Game game;
    private final GraphicUi graphicUI;
    private Timeline timeline;

    public GameController(Game game, GraphicUi graphicUI) {
        this.game = game;
        this.graphicUI = graphicUI;
        graphicUI.renderWorld(game.getWorld());
        graphicUI.getButton1().setOnAction(e -> makeMoves());
        graphicUI.getButton2().setOnAction(e -> restartGame());
        graphicUI.getButton3().setOnAction(e -> startSimulation());
    }

    private void startSimulation() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> makeMoves()));
        timeline.setCycleCount(Timeline.INDEFINITE); // бесконечно
        timeline.play();
    }

    private void restartGame() {
        game.restart();
        graphicUI.renderWorld(game.getWorld());
    }

    private void makeMoves() {
        for (Creature creature : game.getWorld().getCreatures()) {
            Location newLocation = creature.makeMove(game.getWorld());
            if (newLocation == null || !isValidMove(newLocation, creature)) continue;

            if (isValidMove(newLocation, creature)) moveEntity(creature, newLocation);
            else throw new RuntimeException("Move failed");
        }
    }

    private void moveEntity(Creature creature, Location newLocation) {
        Location oldLocation = creature.getLocation();
        game.getWorld().moveCreature(creature, newLocation);
        graphicUI.moveCreature(creature, oldLocation, newLocation);
    }

    private boolean isValidMove(Location newLocation, Creature creature) {
        Entity newLocationEntity = game.getWorld().getEntity(newLocation);
        Location location = creature.getLocation();

        if (newLocation.getRow() < 1 || newLocation.getRow() > game.getWorld().getRows()) return false;
        if (newLocation.getCol() < 1 || newLocation.getCol() > game.getWorld().getColumns()) return false;
        if (newLocation.equals(location)) return false;
        if (newLocationEntity instanceof Rock || newLocationEntity instanceof Tree) return false;

        return (Math.abs(location.getRow() - newLocation.getRow()) <= 1
                && location.getCol() == newLocation.getCol()
                || Math.abs(location.getCol() - newLocation.getCol()) <= 1
                && location.getRow() == newLocation.getRow());
    }
}
