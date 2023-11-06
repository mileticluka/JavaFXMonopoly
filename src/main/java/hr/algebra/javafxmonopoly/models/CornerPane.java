package hr.algebra.javafxmonopoly.models;

import hr.algebra.javafxmonopoly.enums.CornerPaneType;

import java.io.Serializable;

public class CornerPane extends GamePane {

    private final String style = "-fx-background-color: #b0c4de; -fx-border-color: rgba(58,58,58,0.52); -fx-border-width: 1";

    public CornerPane(CornerPaneType type) {

        setWidth(112);
        setHeight(112);

        if (type == CornerPaneType.GO) {
            setupLabels("GO", "", 14);
            setStyle(style);
        }

        if (type == CornerPaneType.GO_TO_JAIL) {
            setupLabels("GO TO JAIL", "", 14);
            setStyle(style);
        }

        if (type == CornerPaneType.FREE_PARKING) {
            setupLabels("FREE PARKING", "", 14);
            setStyle(style);
        }

        if (type == CornerPaneType.JUST_VISITING) {
            setupLabels("JUST VISITING", "", 14);
            setStyle(style);
        }
    }
}

