package hr.algebra.javafxmonopoly;

import hr.algebra.javafxmonopoly.controllers.GameBoardController;
import hr.algebra.javafxmonopoly.controllers.LogPanelController;
import hr.algebra.javafxmonopoly.controllers.GameLogicController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXMonopolyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        GameStateManager manager = new GameStateManager();

        GameBoard gameBoard = new GameBoard();
        GameBoardController gameController = new GameBoardController(gameBoard, manager);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gameBoard);

        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXMonopolyApplication.class.getResource("views/stats-panel.fxml"));
        fxmlLoader.setController(new GameLogicController(manager));
        borderPane.setRight(fxmlLoader.load());

        FXMLLoader fxmlLoader1 = new FXMLLoader(JavaFXMonopolyApplication.class.getResource("views/log-panel.fxml"));
        LogPanelController logPanelController = new LogPanelController();
        fxmlLoader1.setController(logPanelController);
        borderPane.setBottom(fxmlLoader1.load());

        manager.addLogger(logPanelController);


        Scene scene = new Scene(borderPane, 1400, 900);
        stage.setTitle("JavaFX Monopoly");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}