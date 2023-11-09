package hr.algebra.javafxmonopoly.controllers;

import hr.algebra.javafxmonopoly.JavaFXMonopolyApplication;
import hr.algebra.javafxmonopoly.utils.DocumentationUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class DocumentationController {

    MenuBar menuBar;

    public DocumentationController(MenuBar menuBar) {
        this.menuBar = menuBar;

        menuBar.getMenus().get(1).getItems().get(0).setOnAction(this::handleCreateButtonClick);
    }

    private void handleCreateButtonClick(ActionEvent evt) {
        try {
            DocumentationUtils.generateHtmlDocumentationFile("./docs/documentation.html");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Documentation Created Successfully.");
            alert.setHeaderText("Success");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, e.getMessage());
            alert.setHeaderText("Loading Failed");
            alert.showAndWait();
        }
    }

}


