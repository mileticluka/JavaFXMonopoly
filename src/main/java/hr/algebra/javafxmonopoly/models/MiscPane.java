package hr.algebra.javafxmonopoly.models;

import hr.algebra.javafxmonopoly.enums.GamePane;
import hr.algebra.javafxmonopoly.enums.Type;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class MiscPane extends GamePane {

    private String info;

    public MiscPane(String miscName, Type type, String info) {

        this.setHeight(112);
        this.setWidth(64);

        this.setStyle("-fx-background-color: #8a378f; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");

        // Create labels for displaying information
        Label nameLabel = new Label(miscName);
        Label infoLabel = new Label(info);

        // Enable text wrapping
        nameLabel.setWrapText(true);
        infoLabel.setWrapText(true);

        // Set label styles
        nameLabel.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
        infoLabel.setStyle("-fx-font-size: 10;");

        nameLabel.setTextAlignment(TextAlignment.CENTER);
        infoLabel.setTextAlignment(TextAlignment.CENTER);

        // Arrange labels in a VBox
        VBox labelsVBox = new VBox(10);
        labelsVBox.getChildren().addAll(nameLabel, infoLabel);
        labelsVBox.setAlignment(Pos.CENTER);

        // Add VBox to the stack pane
        this.getChildren().add(labelsVBox);

        // Center align labels within the stack pane
        setAlignment(Pos.CENTER);
    }
}