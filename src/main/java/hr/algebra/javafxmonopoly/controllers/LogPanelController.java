package hr.algebra.javafxmonopoly.controllers;

import hr.algebra.javafxmonopoly.network.client.Client;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class LogPanelController {

    @FXML
    transient private ListView logList;

    @FXML
    transient private ListView chatList;

    @FXML
    transient private TextField chatEntry;


    public void init(int playerNumber) {
        chatEntry.setOnKeyPressed(new EventHandler<KeyEvent>() {
            private final int clientNum = playerNumber;

            @Override
            public void handle(KeyEvent keyEvent) {
                if (chatEntry.getText().isEmpty()) return;

                if (keyEvent.getCode() == KeyCode.ENTER) {
                    try {
                        Client.chatService.sendMessage("Player " + this.clientNum + ": "+ chatEntry.getText());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    chatEntry.setText("");
                }
            }
        });


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateChatLog() ));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void updateChatLog()
    {
        try {
            chatList.getItems().clear();
            Client.chatService.getMessages();

            for (String s : Client.chatService.getMessages()) {
                chatList.getItems().add(new Text(s));
                chatList.scrollTo(chatList.getItems().size());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

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
