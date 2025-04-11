package com.alex678;

import com.alex678.entity.Location;
import com.alex678.entity.creature.Creature;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameController {
    private final Game game;
    private final World world;
    private final GraphicUi graphicUI;
    private final Timeline timeline;
    private final Service service;

    public GameController(Game game, GraphicUi graphicUI) {
        this.game = game;
        this.world = game.getWorld();
        this.graphicUI = graphicUI;
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> makeMoves()));
        this.service = new Service(game, world);
        timeline.setCycleCount(Timeline.INDEFINITE);
        graphicUI.renderWorld(game.getWorld());
        graphicUI.getButton1().setOnAction(e -> makeMoves());
        graphicUI.getButton2().setOnAction(e -> restartGame());
        graphicUI.getButton3().setOnAction(e -> startSimulation());
    }

    private void makeMoves() {
        for (Creature creature : new ArrayList<>(world.getCreatures())) {
            Location oldLocation = creature.getLocation();
            Location newLocation = creature.getNewLocation(world);
            if (service.makeMove(creature, newLocation)) {
                graphicUI.moveCreature(oldLocation, newLocation);
            }
        }
    }

    private void restartGame() {
        game.restart();
        graphicUI.renderWorld(world);
    }

    private void startSimulation() {
        timeline.play();
    }
}
