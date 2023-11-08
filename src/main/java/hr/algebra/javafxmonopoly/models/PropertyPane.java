package hr.algebra.javafxmonopoly.models;

import hr.algebra.javafxmonopoly.enums.Group;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.Serializable;

public class PropertyPane extends GamePane{

    private final int price;

    private final Label nameLabel;
    private final Label priceLabel;

    private Boolean bought = false;

    private Player owner;

    public String getName() {
        return nameLabel.getText();
    }

    public int getPrice() {
        return this.price;
    }
    public void setOwnerDirectly(Player p)
    {
        this.owner = p;
        p.addTitleDeed(this);
        this.priceLabel.setText("Owned By: Player " + p.getId());
        this.bought = true;
    }

    public boolean setBought(Player p) {

        if (this.owner == null) {
            this.owner = p;
            this.priceLabel.setText("Owned By: Player " + p.getId());
            p.addTitleDeed(this);
            this.bought = true;
            p.setMoney(p.getMoney() - this.price);
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Cannot Buy Property!");
            alert.setContentText("Property is already owned!");
            alert.showAndWait();
            return false;
        }

    }

    public void setSold(Player p) {
        this.owner = null;
        this.priceLabel.setText("Price: $" + price);
        p.removeTitleDeed(this);
        this.bought = false;
    }

    public PropertyPane(String propertyName, int price, Group group) {
        this.price = price;

        this.setHeight(112);
        this.setWidth(64);

        switch (group) {
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

        nameLabel = new Label(propertyName);
        priceLabel = new Label("Price: $" + price);

        nameLabel.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
        priceLabel.setStyle("-fx-font-size: 10;");

        nameLabel.setWrapText(true);
        priceLabel.setWrapText(true);

        nameLabel.setTextAlignment(TextAlignment.CENTER);
        priceLabel.setTextAlignment(TextAlignment.CENTER);

        VBox labelsVBox = new VBox(10);
        labelsVBox.getChildren().addAll(nameLabel, priceLabel);
        labelsVBox.setAlignment(Pos.CENTER);

        this.getChildren().add(labelsVBox);

        setAlignment(Pos.CENTER);
    }

    public Boolean getBought() {
        return bought;
    }

    public Player getOwner() {
        return this.owner;
    }
}


