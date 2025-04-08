package com.alex678;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GraphicUi {
    private VBox root;
    private Text text;
    private GridPane grid;
    private StackPane[][] board;
    private HBox buttonBox;
    private Button button1;
    private Button button2;
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
        button1 = new Button("Кнопка1");
        button2 = new Button("Кнопка 2");
        buttonBox.getChildren().addAll(button1, button2);
        root.getChildren().addAll(text, grid, buttonBox);
    }

    public VBox getRoot() {
        return root;
    }

    public StackPane getCell (int row, int col) {
        return board[row][col];
    }

    public Button getButton1() {
        return button1;
    }

    public Button getButton2() {
        return button2;
    }
}