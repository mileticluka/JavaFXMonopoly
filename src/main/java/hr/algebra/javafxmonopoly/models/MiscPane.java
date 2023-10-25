package hr.algebra.javafxmonopoly.models;

import hr.algebra.javafxmonopoly.enums.Type;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class MiscPane extends GamePane {

    private String info;
    private final Type type;

    public MiscPane(String miscName, Type type, String info) {

        this.type = type;

        this.setHeight(112);
        this.setWidth(64);

        this.setStyle("-fx-background-color: #8a378f; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");

        Label nameLabel = new Label(miscName);
        Label infoLabel = new Label(info);

        nameLabel.setWrapText(true);
        infoLabel.setWrapText(true);

        nameLabel.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
        infoLabel.setStyle("-fx-font-size: 10;");

        nameLabel.setTextAlignment(TextAlignment.CENTER);
        infoLabel.setTextAlignment(TextAlignment.CENTER);

        VBox labelsVBox = new VBox(10);
        labelsVBox.getChildren().addAll(nameLabel, infoLabel);
        labelsVBox.setAlignment(Pos.CENTER);

        this.getChildren().add(labelsVBox);

        setAlignment(Pos.CENTER);
    }

    public Type getType() {
        return this.type;
    }
}