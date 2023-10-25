package hr.algebra.javafxmonopoly.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class LogPanelController {

    @FXML
    private ListView logList;
    
    public void addLog(String str) {
        logList.getItems().add(str);
        logList.scrollTo(logList.getItems().size());
    }
}
