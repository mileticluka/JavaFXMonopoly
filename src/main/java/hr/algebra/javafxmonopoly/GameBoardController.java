package hr.algebra.javafxmonopoly;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// GameBoardController.java
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoardController {

    private GameStateManager gameStateManager;

    public GameBoardController(GameBoard gameBoard, GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;

        for (StackPane pane : gameBoard.getStackPanes()) {
            pane.setOnMouseClicked(event -> handlePaneClick(pane));
        }
    }

    private void handlePaneClick(StackPane pane) {
        Player currentPlayer = gameStateManager.getCurrentPlayer();
        System.out.println("Player " + currentPlayer.getId() + " clicked on " + pane.getId());
    }

}
