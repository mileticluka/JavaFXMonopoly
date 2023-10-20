package hr.algebra.javafxmonopoly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXMonopolyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        GameBoard gameBoard = new GameBoard();
        GameController gameController = new GameController(gameBoard);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gameBoard);

        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXMonopolyApplication.class.getResource("views/stats-panel.fxml"));
        borderPane.setRight(fxmlLoader.load());

        Scene scene = new Scene(borderPane, 1400, 800);
        stage.setTitle("JavaFX Monopoly");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}