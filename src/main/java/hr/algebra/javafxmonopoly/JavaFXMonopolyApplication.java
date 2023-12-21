package hr.algebra.javafxmonopoly;

import hr.algebra.javafxmonopoly.controllers.*;
import hr.algebra.javafxmonopoly.network.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXMonopolyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        GameStateManager manager = new GameStateManager();
        manager.connectToServer();

        GameBoard gameBoard = new GameBoard();
        new GameBoardController(gameBoard, manager);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gameBoard);

        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXMonopolyApplication.class.getResource("views/stats-panel.fxml"));
        GameLogicController gameLogicController = new GameLogicController(manager);
        fxmlLoader.setController(gameLogicController);
        borderPane.setRight(fxmlLoader.load());

        FXMLLoader fxmlLoader1 = new FXMLLoader(JavaFXMonopolyApplication.class.getResource("views/log-panel.fxml"));
        LogPanelController logPanelController = new LogPanelController();
        fxmlLoader1.setController(logPanelController);
        borderPane.setBottom(fxmlLoader1.load());

        manager.addLogger(logPanelController);

        Menu serializationMenu = new Menu("Serialization");
        serializationMenu.getItems().add(new MenuItem("Save Game State"));
        serializationMenu.getItems().add(new MenuItem("Load Game State"));

        Menu documentationMenu = new Menu("Documentation");
        documentationMenu.getItems().add(new MenuItem("Create Documentation"));

        MenuBar menuBar = new MenuBar(serializationMenu,documentationMenu);

        SerializationController serializationController = new SerializationController(menuBar,manager,gameLogicController,gameBoard);
        new DocumentationController(menuBar);

        manager.setSerializationController(serializationController);

        borderPane.setTop(menuBar);

        Scene scene = new Scene(borderPane, 1400, 900);
        stage.setTitle("Client: " + String.valueOf(manager.client.getClientID()));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}