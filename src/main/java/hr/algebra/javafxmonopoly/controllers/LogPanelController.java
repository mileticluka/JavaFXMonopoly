package hr.algebra.javafxmonopoly.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LogPanelController {

    @FXML
    transient private ListView logList;
    
    public void addLog(String str, List<String> logs) {
        logs.add(str);
        logList.getItems().add(str);
        logList.scrollTo(logList.getItems().size());
    }

    public void setLogs(List<String> logs)
    {
        this.logList.getItems().clear();
        this.logList.getItems().setAll(logs);
        logList.scrollTo(logList.getItems().size());
    }
}
