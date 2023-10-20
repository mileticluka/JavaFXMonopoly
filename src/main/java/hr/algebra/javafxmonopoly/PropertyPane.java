package hr.algebra.javafxmonopoly;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class PropertyPane extends StackPane {

    private String propertyName;
    private int price;
    private Group group;

    public PropertyPane(String propertyName, int price, Group group) {
        this.propertyName = propertyName;
        this.price = price;
        this.group = group;

        this.setHeight(112);
        this.setWidth(64);

        switch (this.group) {
            case BROWN:
                this.setStyle("-fx-background-color: #855100; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
                break;
            case LIGHT_BLUE:
                this.setStyle("-fx-background-color: #5cfff9; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
                break;
            case PINK:
                this.setStyle("-fx-background-color: #ff3ef6; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
                break;
            case ORANGE:
                this.setStyle("-fx-background-color: #f38c09; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
                break;
            case RED:
                this.setStyle("-fx-background-color: #f12f2f; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
                break;
            case YELLOW:
                this.setStyle("-fx-background-color: #fffa1d; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
                break;
            case GREEN:
                this.setStyle("-fx-background-color: #50ee28; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
                break;
            case BLUE:
                this.setStyle("-fx-background-color: #1860cb; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
                break;
            case AIRPORT:
                this.setStyle("-fx-background-color: #9d9db2; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
                break;
            case COMPANY:
                this.setStyle("-fx-background-color: #a9bb6e; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
                break;
            default:
                // Default code if no match is found
                break;
        }

        // Create labels for displaying information
        Label nameLabel = new Label(propertyName);
        Label priceLabel = new Label("Price: $" + price);

        // Set label styles
        nameLabel.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
        priceLabel.setStyle("-fx-font-size: 10;");

        // Enable wrapping text
        nameLabel.setWrapText(true);
        priceLabel.setWrapText(true);

        nameLabel.setTextAlignment(TextAlignment.CENTER);
        priceLabel.setTextAlignment(TextAlignment.CENTER);

        // Arrange labels in a VBox
        VBox labelsVBox = new VBox(10); // Adjust the spacing by changing the value (e.g., VBox(10))
        labelsVBox.getChildren().addAll(nameLabel, priceLabel);
        labelsVBox.setAlignment(Pos.CENTER);

        // Add VBox to the stack pane
        this.getChildren().add(labelsVBox);

        // Center align labels within the stack pane
        setAlignment(Pos.CENTER);
    }
}

enum Group {
    BROWN,
    LIGHT_BLUE,
    PINK,
    ORANGE,
    RED,
    YELLOW,
    GREEN,
    BLUE,
    AIRPORT,
    COMPANY
}
