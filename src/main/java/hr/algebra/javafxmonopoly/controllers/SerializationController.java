package hr.algebra.javafxmonopoly.controllers;

import hr.algebra.javafxmonopoly.GameBoard;
import hr.algebra.javafxmonopoly.GameStateManager;
import hr.algebra.javafxmonopoly.GameStateSerializable;
import hr.algebra.javafxmonopoly.models.Player;
import hr.algebra.javafxmonopoly.utils.SerializationUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class SerializationController {

    GameBoard gameBoard;
    MenuBar menuBar;
    GameStateManager gameStateManager;
    GameLogicController gameLogicController;
    GameStateSerializable gameStateSerializable;

    public SerializationController(MenuBar menuBar, GameStateManager gameStateManager, GameLogicController gameLogicController, GameBoard gameBoard) {
        this.menuBar = menuBar;
        this.gameStateManager = gameStateManager;
        this.gameLogicController = gameLogicController;
        this.gameBoard = gameBoard;

        this.gameStateSerializable = new GameStateSerializable();

        menuBar.getMenus().get(0).getItems().get(0).setOnAction(this::handleSaveButtonClick);
        menuBar.getMenus().get(0).getItems().get(1).setOnAction(this::handleLoadButtonClick);
    }

    private void handleSaveButtonClick(ActionEvent evt) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(new Stage());

        if(file == null)
        {
            System.out.println("NOT SAVED!");
            return;
        }


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
            System.out.println("FILE DOESN'T EXIST!");
            return;
        }

        for(Player p : this.gameStateManager.getPlayers())
        {
            this.gameStateManager.getGamePanes().get(p.getPosition()).erasePlayer(p.getId());
        }

        try {
            gameStateSerializable = SerializationUtils.read(file);
            this.gameStateManager.setProperties(gameStateSerializable);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        gameStateManager.logger.setLogs(gameStateManager.getLogs());


        gameLogicController.setPlayerPanelsDirectly(gameStateManager.getCurrentPlayer());

        for(Player p : gameStateManager.getPlayers())
        {
            gameStateManager.getGamePanes().get(p.getPosition()).drawPlayer(p.getId());
            p.loadDeeds(gameStateManager.getGamePanes());
            gameLogicController.updateDeedList(p);


        }
        gameLogicController.updateMoneyLabels();

        System.out.println(gameStateManager);

        System.out.println("LOADED!");

    }

    public static byte[] serializeIntoBytes(GameStateManager gameStateManager)
    {
        GameStateSerializable outGS = new GameStateSerializable();
        outGS.setProperties(gameStateManager);

        byte[] out;

        try {
            out = SerializationUtils.serialize(outGS);
        } catch (IOException ex) {
            out = new byte[0];
            ex.printStackTrace();
        }

        System.out.println("Serialized!");
        return out;
    }


    public void deserializeAndLoad(byte[] bytes)
    {
        for(Player p : this.gameStateManager.getPlayers())
        {
            this.gameStateManager.getGamePanes().get(p.getPosition()).erasePlayer(p.getId());
        }

        try {
            gameStateSerializable = SerializationUtils.deserialize(bytes, GameStateSerializable.class);
            gameStateManager.setProperties(gameStateSerializable);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        gameStateManager.logger.setLogs(gameStateManager.getLogs());

        if(gameStateManager.client.getClientID() == gameStateManager.client.getClientID())
        {
            gameLogicController.setPlayerPanelsDirectly(gameStateManager.getCurrentPlayer());
        }

        for(Player p : gameStateManager.getPlayers())
        {
            gameStateManager.getGamePanes().get(p.getPosition()).drawPlayer(p.getId());
            p.loadDeeds(gameStateManager.getGamePanes());
            gameLogicController.updateDeedList(p);
        }
        gameLogicController.updateMoneyLabels();

        System.out.println(gameStateManager);

        System.out.println("LOADED!");
    }


}


