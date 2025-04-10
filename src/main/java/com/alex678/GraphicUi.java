package com.alex678;

import com.alex678.entity.Entity;
import com.alex678.entity.Location;
import com.alex678.entity.creature.Creature;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.Getter;

public class GraphicUi {
    @Getter
    private final VBox root;
    private final Text text;
    private final GridPane grid;
    private final StackPane[][] board;
    private final HBox buttonBox;
    @Getter
    private final Button button1;
    @Getter
    private final Button button2;
    @Getter
    private final Button button3;
    private final String cellStyle =
            "-fx-border-color: black; " +
                    "-fx-background-color: white; " +
                    "-fx-border-width: 0.3";

    public GraphicUi() {
        root = new VBox();
        text = new Text("Игровое поле");
        grid = new GridPane();
        board = new StackPane[10][25];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 25; col++) {
                StackPane cell = new StackPane();
                cell.setStyle(cellStyle);
                cell.setPrefSize(40, 40);
                board[row][col] = cell;
                grid.add(cell, col, row);
            }
        }
        buttonBox = new HBox();
        button1 = new Button("Сделать ход");
        button2 = new Button("Перезапустить");
        button3 = new Button("Запустить симуляцию");
        buttonBox.getChildren().addAll(button1, button2, button3);
        root.getChildren().addAll(text, grid, buttonBox);
    }

    public void renderWorld(World world) {
        clearBoard();
        for (Entity entity : world.getEntities()) {
            putEntityOnBoard(entity);
        }
    }

    public void clearBoard() {
        for (StackPane[] stackPanes : board) {
            for (StackPane cell : stackPanes) {
                cell.getChildren().removeIf(node -> node instanceof ImageView);
            }
        }
    }

    private void putEntityOnBoard(Entity entity) {
        Location location = entity.getLocation();
        StackPane cell = getCell(location);
        ImageView imageView = new ImageView(entity.getImage());
        imageView.setFitHeight(36);
        imageView.setFitWidth(36);
        cell.getChildren().add(imageView);
    }

    private StackPane getCell(Location location) {
        return board[location.getRow() - 1][location.getCol() - 1];
    }

    public void moveCreature(Creature creature, Location oldLocation, Location newLocation) {
        StackPane oldCell = getCell(oldLocation);
        StackPane newCell = getCell(newLocation);

        newCell.getChildren().removeIf(node -> node instanceof ImageView);

        ImageView imageView = null;
        for (Node node : oldCell.getChildren()) {
            if (node instanceof ImageView) {
                imageView = (ImageView) node;
                break;
            }
        }

        if (imageView == null) return;
        oldCell.getChildren().remove(imageView);
        newCell.getChildren().add(imageView);

        double fromX = (oldLocation.getCol() - newLocation.getCol()) * 40;
        double fromY = (oldLocation.getRow() - newLocation.getRow()) * 40;

        imageView.setTranslateX(fromX);
        imageView.setTranslateY(fromY);

        TranslateTransition transition = new TranslateTransition(Duration.millis(200), imageView);
        transition.setToX(0);
        transition.setToY(0);

        transition.play();
    }
}