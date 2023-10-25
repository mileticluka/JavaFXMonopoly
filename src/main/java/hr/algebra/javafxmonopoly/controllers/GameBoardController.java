package hr.algebra.javafxmonopoly.controllers;

import hr.algebra.javafxmonopoly.GameBoard;
import hr.algebra.javafxmonopoly.models.GamePane;
import hr.algebra.javafxmonopoly.GameStateManager;
import hr.algebra.javafxmonopoly.models.Player;
import javafx.scene.input.MouseEvent;

public class GameBoardController {

    private final GameStateManager gameStateManager;

    public GameBoardController(GameBoard gameBoard, GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;

        gameStateManager.setGamePanes(gameBoard.getGamePanes());

        for (GamePane pane : gameBoard.getGamePanes()) {
            pane.setOnMouseClicked(event -> handlePaneClick(pane, event));
        }

        for (Player p : gameStateManager.getPlayers()) {
            gameStateManager.getGamePanes().get(0).drawPlayer(p.getId());
        }
    }

    private void handlePaneClick(GamePane pane, MouseEvent evt) {
        Player currentPlayer = gameStateManager.getCurrentPlayer();
        System.out.println("Player " + currentPlayer.getId() + " clicked on " + pane.getId());
    }

}
