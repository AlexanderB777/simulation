package com.alex678;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Game game = new Game();
        GraphicUi ui = new GraphicUi();
        GameController controller = new GameController(game, ui);

        Scene scene = new Scene(ui.getRoot(), 1000, 450);
        stage.setTitle("Game Simulation");
        stage.setScene(scene);
        stage.show();
    }
}