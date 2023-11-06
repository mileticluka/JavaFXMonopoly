package hr.algebra.javafxmonopoly.controllers;

import hr.algebra.javafxmonopoly.GameBoard;
import hr.algebra.javafxmonopoly.GameStateManager;
import hr.algebra.javafxmonopoly.GameStateSerializable;
import hr.algebra.javafxmonopoly.models.GamePane;
import hr.algebra.javafxmonopoly.models.Player;
import hr.algebra.javafxmonopoly.models.SerializationUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MenuController {

    GameBoard gameBoard;
    MenuBar menuBar;
    GameStateManager gameStateManager;
    GameLogicController gameLogicController;

    GameStateSerializable gameStateSerializable;

    public MenuController(MenuBar menuBar, GameStateManager gameStateManager, GameLogicController gameLogicController, GameBoard gameBoard) {
        this.menuBar = menuBar;
        this.gameStateManager = gameStateManager;
        this.gameLogicController = gameLogicController;
        this.gameBoard = gameBoard;

        this.gameStateSerializable = new GameStateSerializable();

        menuBar.getMenus().getFirst().getItems().getFirst().setOnAction(this::handleSaveButtonClick);
        menuBar.getMenus().getFirst().getItems().getLast().setOnAction(this::handleLoadButtonClick);
    }

    private void handleSaveButtonClick(ActionEvent evt) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(new Stage());

        this.gameStateSerializable.setProperties(gameStateManager);

        try {
            SerializationUtils.write(gameStateSerializable,file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("SAVED!");
    }

    private void handleLoadButtonClick(ActionEvent evt) {

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());

        if(file == null)
        {
            System.out.println("NO FILE SELECTED!");
            return;
        }
        if(!file.exists())
        {
            System.out.println("FILE DOESNT EXIST!");
            return;
        }

        for(Player p : this.gameStateManager.getPlayers())
        {
            this.gameStateManager.getGamePanes().get(p.getPosition()).erasePlayer(p.getId());
        }

        try {
            this.gameStateManager.setProperties(gameStateSerializable);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        for(Player p : this.gameStateManager.getPlayers())
        {
            p.loadDeeds(this.gameStateManager.getGamePanes());
            this.gameStateManager.getGamePanes().get(p.getId()).drawPlayer(p.getId());
        }

        gameLogicController.setPlayerPanelsDirectly(gameStateManager.getCurrentPlayer());

        System.out.println(this.gameStateManager.getCurrentPlayerTurn());
        System.out.println(this.gameStateManager.getCurrentPlayer().getId());

        System.out.println("LOADED!");

    }

}


