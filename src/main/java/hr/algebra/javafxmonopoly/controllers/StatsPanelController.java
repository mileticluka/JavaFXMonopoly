package hr.algebra.javafxmonopoly.controllers;

import hr.algebra.javafxmonopoly.GameStateManager;
import hr.algebra.javafxmonopoly.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatsPanelController {

    private GameStateManager gameStateManager;

    @FXML
    private Pane player1Pane;

    @FXML
    private Pane player2Pane;

    @FXML
    private Pane player3Pane;

    @FXML
    private Pane player4Pane;

    @FXML
    private Button p1btn;

    @FXML
    private Button p2btn;

    @FXML
    private Button p3btn;

    @FXML
    private Button p4btn;

    @FXML
    private Label p1MoneyLabel;

    @FXML
    private Label p2MoneyLabel;

    @FXML
    private Label p3MoneyLabel;

    @FXML
    private Label p4MoneyLabel;

    private List<Pane> panes;
    private List<Button> buttons;

    private int currentPlayerTurn = 0;

    public StatsPanelController(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    List<Player> players;

    @FXML
    private void initialize() {
        panes = new ArrayList<>();
        panes.add(player1Pane);
        panes.add(player2Pane);
        panes.add(player3Pane);
        panes.add(player4Pane);

        buttons = new ArrayList<>();
        buttons.add(p1btn);
        buttons.add(p2btn);
        buttons.add(p3btn);
        buttons.add(p4btn);

        players = gameStateManager.getPlayers();

        for (Pane p : panes) {
            p.setDisable(true);
        }
        player1Pane.setDisable(false);

        // Set up click event for all buttons
        for (Button button : buttons) {
            setClickEvent(button);
        }

        // Update money labels
        updateMoneyLabels();
    }

    private void setClickEvent(Button button) {
        button.setOnMouseClicked(event -> handleButtonClick());
    }

    private void handleButtonClick() {
        Player currentPlayer = players.get(currentPlayerTurn);
        int diceRoll = rollDice();
        currentPlayer.setPosition((currentPlayer.getPosition() + diceRoll) % 40);

        // Disable current player pane
        panes.get(currentPlayerTurn).setDisable(true);

        // Switch to the next player
        currentPlayerTurn = (currentPlayerTurn + 1) % players.size();
        gameStateManager.nextPlayerTurn();

        // Enable next player pane
        panes.get(currentPlayerTurn).setDisable(false);

        // Update the money label and other UI elements
        updateMoneyLabels();
        System.out.println("Player " + currentPlayer.getId() + " rolled a " + diceRoll + ". New position: " + currentPlayer.getPosition());
    }

    private int rollDice() {
        // Simulate a 2 six-sided dice roll
        Random random = new Random();
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        return dice1 + dice2;
    }

    private void updateMoneyLabels() {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Label moneyLabel = getMoneyLabel(i + 1);
            moneyLabel.setText("$" + player.getMoney());
        }
    }

    private Label getMoneyLabel(int playerNumber) {
        switch (playerNumber) {
            case 1:
                return p1MoneyLabel;
            case 2:
                return p2MoneyLabel;
            case 3:
                return p3MoneyLabel;
            case 4:
                return p4MoneyLabel;
            default:
                throw new IllegalArgumentException("Invalid player number: " + playerNumber);
        }
    }
}