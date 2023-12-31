package hr.algebra.javafxmonopoly.models;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GamePane extends StackPane {

    private static final int MAX_PLAYERS = 4;
    private final List<Circle> playerCircles = new ArrayList<>();
    private VBox playerCircleContainer;
    private VBox labelsVBox;

    public GamePane() {
        initialize();
    }

    private void initialize() {
        setAlignment(Pos.CENTER);

        playerCircleContainer = new VBox(5);
        playerCircleContainer.setAlignment(Pos.CENTER);

        createLabelsVBox();

        getChildren().addAll(playerCircleContainer, labelsVBox);
    }

    private void createLabelsVBox() {
        labelsVBox = new VBox(10);
        labelsVBox.setAlignment(Pos.CENTER);
    }

    public void addPlayerCircle(int playerId, Color color) {
        if (playerCircles.size() < MAX_PLAYERS) {
            Circle playerCircle = createPlayerCircle(color);
            playerCircle.setUserData(playerId);
            playerCircles.add(playerCircle);
            updatePlayerCirclePositions();
        } else {
            System.out.println("Maximum number of players reached.");
        }
    }

    public void drawPlayer(int playerId) {
        if (playerId >= 1 && playerId <= MAX_PLAYERS) {
            Color playerColor = getPlayerColor(playerId);
            addPlayerCircle(playerId, playerColor);
        } else {
            System.out.println("Invalid player ID: " + playerId);
        }
    }

    public void erasePlayer(int playerId) {
        if (playerId >= 1 && playerId <= MAX_PLAYERS) {
            playerCircles.removeIf(circle -> circle.getUserData() != null && (int) circle.getUserData() == playerId);
            updatePlayerCirclePositions();
        } else {
            System.out.println("Invalid player ID: " + playerId);
        }
    }

    private Color getPlayerColor(int playerId) {
        switch (playerId) {
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.RED;
            default:
                return Color.BLACK;
        }
    }

    private Circle createPlayerCircle(Color color) {
        Circle circle = new Circle(5, color);
        circle.setStroke(Color.BLACK);
        return circle;
    }

    private void updatePlayerCirclePositions() {
        playerCircleContainer.getChildren().clear();
        playerCircleContainer.getChildren().addAll(playerCircles);
    }

    protected void setupLabels(String name, String info, int fontSize) {
        labelsVBox.getChildren().clear();

        Label nameLabel = new Label(name);
        Label infoLabel = new Label(info);

        nameLabel.setWrapText(true);
        infoLabel.setWrapText(true);

        nameLabel.setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: bold;");
        infoLabel.setStyle("-fx-font-size: " + fontSize + ";");

        nameLabel.setTextAlignment(TextAlignment.CENTER);
        infoLabel.setTextAlignment(TextAlignment.CENTER);

        // Arrange labels in a VBox
        labelsVBox.getChildren().addAll(nameLabel, infoLabel);
    }

}
