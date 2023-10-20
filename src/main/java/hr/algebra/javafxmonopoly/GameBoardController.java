package hr.algebra.javafxmonopoly;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class GameController {
    public GameController(GameBoard gameBoard) {

        for (StackPane p: gameBoard.getStackPanes())
        {
            p.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println(p.getId());
                }
            });
        }
    }

}