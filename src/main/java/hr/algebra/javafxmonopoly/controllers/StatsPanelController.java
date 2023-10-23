package hr.algebra.javafxmonopoly.controllers;

import hr.algebra.javafxmonopoly.enums.GamePane;
import hr.algebra.javafxmonopoly.GameStateManager;
import hr.algebra.javafxmonopoly.models.Player;
import hr.algebra.javafxmonopoly.models.PropertyPane;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatsPanelController {

    private final GameStateManager gameStateManager;

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

    @FXML
    private ListView p1DeedListView;

    @FXML
    private ListView p2DeedListView;

    @FXML
    private ListView p3DeedListView;

    @FXML
    private ListView p4DeedListView;

    @FXML
    private Button p1btnBuy;
    @FXML
    private Button p2btnBuy;
    @FXML
    private Button p3btnBuy;
    @FXML
    private Button p4btnBuy;

    @FXML
    private Label p1lblPlace;
    @FXML
    private Label p2lblPlace;
    @FXML
    private Label p3lblPlace;
    @FXML
    private Label p4lblPlace;

    private List<Pane> panes;

    private List<Label> placeLabels;

    private List<Button> buyButtons;

    private List<ListView> deedLists;

    private int currentPlayerTurn = 0;
    List<Player> players;

    public StatsPanelController(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }



    @FXML
    private void initialize() {

        panes = new ArrayList<>();
        panes.add(player1Pane);
        panes.add(player2Pane);
        panes.add(player3Pane);
        panes.add(player4Pane);

        List<Button> rollButtons = new ArrayList<>();
        rollButtons.add(p1btn);
        rollButtons.add(p2btn);
        rollButtons.add(p3btn);
        rollButtons.add(p4btn);

        placeLabels = new ArrayList<>();
        placeLabels.add(p1lblPlace);
        placeLabels.add(p2lblPlace);
        placeLabels.add(p3lblPlace);
        placeLabels.add(p4lblPlace);

        buyButtons = new ArrayList<>();
        buyButtons.add(p1btnBuy);
        buyButtons.add(p2btnBuy);
        buyButtons.add(p3btnBuy);
        buyButtons.add(p4btnBuy);

        deedLists = new ArrayList<>();
        deedLists.add(p1DeedListView);
        deedLists.add(p2DeedListView);
        deedLists.add(p3DeedListView);
        deedLists.add(p4DeedListView);

        players = gameStateManager.getPlayers();

        for (Pane p : panes) {
            p.setDisable(true);
        }
        player1Pane.setDisable(false);

        // Set up click event for all buttons
        for (Button button : rollButtons) {
            button.setOnMouseClicked(event -> handleRollButtonClick());
        }

        for (Button button : buyButtons){
            button.setOnMouseClicked(mouseEvent -> handleBuyButtonClick(button));
            button.setDisable(true);
        }

        // Update money labels
        updateMoneyLabels();
    }

    private void handleRollButtonClick() {

        Player currentPlayer = players.get(currentPlayerTurn);
        int oldPos = currentPlayer.getPosition();

        int diceRoll = rollDice();

        gameStateManager.getGamePanes().get(currentPlayer.getPosition()).erasePlayer(currentPlayer.getId());

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

        gameStateManager.getGamePanes().get(currentPlayer.getPosition()).drawPlayer(currentPlayer.getId());

        GamePane newGamePane = gameStateManager.getGamePanes().get(currentPlayer.getPosition());

        if(newGamePane instanceof PropertyPane && !((PropertyPane) newGamePane).getBought())
        {
            buyButtons.get(currentPlayer.getId()-1).setDisable(false);
            placeLabels.get(currentPlayer.getId()-1).setText(((PropertyPane) newGamePane).getName());
        }
        else{
            buyButtons.get(currentPlayer.getId()-1).setDisable(true);
            placeLabels.get(currentPlayer.getId()-1).setText("");
        }

        int newPos = currentPlayer.getPosition();

        if(oldPos > newPos)
        {
            currentPlayer.setMoney(currentPlayer.getMoney()+200);
            System.out.println("Player " + currentPlayer.getId() + " passed the Start and gained $200.");
        }


        //Sending Money from stepping
        if(newGamePane instanceof PropertyPane && ((PropertyPane) newGamePane).getBought() && !((PropertyPane) newGamePane).getOwner().equals(currentPlayer))
        {
            Player owner = (((PropertyPane) newGamePane).getOwner());


            PropertyPane ownersPane = ((PropertyPane) newGamePane);

            int toll = (int) (ownersPane.getPrice()*0.10);

            owner.setMoney((int) (owner.getMoney()+toll));
            currentPlayer.setMoney(currentPlayer.getMoney()-toll);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Paid: " + toll);
            alert.setContentText("Paid to: player " + owner.getId());
            alert.showAndWait();
        }

        //TODO: ADD SOME KIND OF LOGIC FOR LOSING THE GAME

        updateMoneyLabels();

    }

    private void handleBuyButtonClick(Button sender){


        Player currentPlayer = players.get(currentPlayerTurn);
        PropertyPane currentPane = (PropertyPane) gameStateManager.getGamePanes().get(currentPlayer.getPosition());

        if(currentPlayer.getMoney() >= currentPane.getPrice())
        {
            currentPane.setBought(currentPlayer);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Cannot Buy Property!");
            alert.setContentText("Not Enough Money!");
            alert.showAndWait();
        }

        List<String> deeds = new ArrayList<>();
        for(PropertyPane p : currentPlayer.getTitleDeeds())
        {
            deeds.add(p.getName());
        }

        deedLists.get(currentPlayer.getId() - 1).getItems().setAll(deeds);

        buyButtons.get(currentPlayer.getId()-1).setDisable(true);

        updateMoneyLabels();

        System.out.println("Player " + currentPlayer.getId() + " bought " + currentPane.getName() + " for $" + currentPane.getPrice() + ".");


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