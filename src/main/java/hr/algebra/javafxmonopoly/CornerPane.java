package hr.algebra.javafxmonopoly;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CornerPane extends StackPane {

    private CornerPaneType type;
    private ImageView img;

    CornerPane(CornerPaneType type) {
        this.setWidth(112);
        this.setHeight(112);

        this.type = type;

        if (this.type == CornerPaneType.GO) {
            this.img = getImageView("img/go.png");
        }

        if (this.type == CornerPaneType.GO_TO_JAIL) {
            this.img = getImageView("img/go-to-jail.png");
        }

        if (this.type == CornerPaneType.FREE_PARKING) {
            this.img = getImageView("img/free-parking.png");
        }

        if (this.type == CornerPaneType.JUST_VISITING) {
            this.img = getImageView("img/just-visiting.jpg");
        }

        this.img.fitWidthProperty().bind(this.widthProperty());
        this.img.fitHeightProperty().bind(this.heightProperty());

        this.getChildren().add(this.img);

    }

    ImageView getImageView(String path) {
        ImageView imageView = new ImageView(String.valueOf(JavaFXMonopolyApplication.class.getResource(path)));
        return imageView;
    }

}


enum CornerPaneType
{
    GO,
    GO_TO_JAIL,
    FREE_PARKING,
    JUST_VISITING
}
