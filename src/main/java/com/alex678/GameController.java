package com.alex678;

import com.alex678.entity.Entity;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GameController {
    private Game game;
    private GraphicUi graphicUI;

    public GameController(Game game, GraphicUi graphicUI) {
        this.game = game;
        this.graphicUI = graphicUI;

        initField();

    }

    private void initField() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 25; col++) {
                StackPane cell = graphicUI.getCell(row, col);
                Entity entity = game.getCell(row, col);
                if (entity != null) {
                    ImageView imageView = new ImageView(entity.getImage());
                    imageView.setFitHeight(36);
                    imageView.setFitWidth(36);
                    cell.getChildren().add(imageView);
                }

            }
        }
    }
}
