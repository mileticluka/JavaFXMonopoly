package hr.algebra.javafxmonopoly.models;

import hr.algebra.javafxmonopoly.enums.CornerPaneType;

public class CornerPane extends GamePane {

    private final CornerPaneType type;

    public CornerPane(CornerPaneType type) {
        this.type = type;
        initialize();
    }

    private void initialize() {
        setWidth(112);
        setHeight(112);

        if (this.type == CornerPaneType.GO) {
            setupLabels("GO", "", 14); // Set font size to 14
            setStyle("-fx-background-color: #b0c4de; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
        }

        if (this.type == CornerPaneType.GO_TO_JAIL) {
            setupLabels("GO TO JAIL", "", 14); // Set font size to 14
            setStyle("-fx-background-color: #b0c4de; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
        }

        if (this.type == CornerPaneType.FREE_PARKING) {
            setupLabels("FREE PARKING", "", 14); // Set font size to 14
            setStyle("-fx-background-color: #b0c4de; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
        }

        if (this.type == CornerPaneType.JUST_VISITING) {
            setupLabels("JUST VISITING", "", 14); // Set font size to 14
            setStyle("-fx-background-color: #b0c4de; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1");
        }
    }
}

