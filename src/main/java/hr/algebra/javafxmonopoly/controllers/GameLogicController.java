package hr.algebra.javafxmonopoly.controllers;

import hr.algebra.javafxmonopoly.GameStateSerializable;
import hr.algebra.javafxmonopoly.models.GamePane;
import hr.algebra.javafxmonopoly.GameStateManager;
import hr.algebra.javafxmonopoly.models.MiscPane;
import hr.algebra.javafxmonopoly.models.Player;
import hr.algebra.javafxmonopoly.models.PropertyPane;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogicController {

    private final GameStateManager gameStateManager;


    //region UI Elements Declarations...
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

    //endregion

    public GameLogicController(GameStateManager gameStateManager) {
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

        for (Pane p : panes) {
            p.setDisable(true);
        }
        if(gameStateManager.client.getClientID() <= 1 )
        {
            player1Pane.setDisable(false);
        }

        for (Button button : rollButtons) {
            button.setOnMouseClicked(event -> handleRollButtonClick());
        }

        for (Button button : buyButtons) {
            button.setOnMouseClicked(mouseEvent -> handleBuyButtonClick(button));
            button.setDisable(true);
        }

        GameStateSerializable gss = new GameStateSerializable();
        gss.setProperties(this.gameStateManager);

        updateMoneyLabels();
    }

    private void handleRollButtonClick() {

        int diceRoll = rollDice();
        Player currentPlayer = gameStateManager.getPlayers().get(gameStateManager.getCurrentPlayerTurn());
        int oldPos = currentPlayer.getPosition();

        GamePane newGamePane = movePlayer(currentPlayer, diceRoll);

        toggleBuyButton(currentPlayer, newGamePane);

        //Handle Looping over start pane
        int newPos = currentPlayer.getPosition();
        if (oldPos > newPos) {
            currentPlayer.setMoney(currentPlayer.getMoney() + 200);
            gameStateManager.logger.addLog("Player " + currentPlayer.getId() + " passed the Start and gained $200.",gameStateManager.getLogs());
            //System.out.println("Player " + currentPlayer.getId() + " passed the Start and gained $200.");
        }

        //Handle stepping on property
        if (newGamePane instanceof PropertyPane && ((PropertyPane) newGamePane).getBought() && !((PropertyPane) newGamePane).getOwner().equals(currentPlayer)) {
            handlePropertyStep(currentPlayer, newGamePane);
        } else if (newGamePane instanceof MiscPane) {
            handleMiscStep(currentPlayer, newGamePane);
        }

        updateMoneyLabels();

        if (currentPlayer.getMoney() <= 0) {
            handleBankrupt(currentPlayer);
        }

        togglePlayerPanels(currentPlayer);

        GameStateSerializable gst = new GameStateSerializable();
        gst.setProperties(gameStateManager);
        gameStateManager.history.add(gst);
        gameStateManager.historyIndex ++;

        try {
            byte[] serializedData = SerializationController.serializeIntoBytes(this.gameStateManager);
            this.gameStateManager.client.getCSC().getOutputStream().writeInt(serializedData.length);
            this.gameStateManager.client.getCSC().getOutputStream().write(serializedData);
            this.gameStateManager.client.getCSC().getOutputStream().flush();
            System.out.println("Written to out!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void handleBankrupt(Player currentPlayer) {
        currentPlayer.setBankrupt();

        GamePane currentPane = gameStateManager.getGamePanes().get(currentPlayer.getPosition());
        currentPane.erasePlayer(currentPlayer.getId());

        gameStateManager.logger.addLog("Player " + currentPlayer.getId() + " Bankrupted and has been removed from the game.",gameStateManager.getLogs());
    }

    private GamePane movePlayer(Player currentPlayer, int diceRoll) {

        GamePane oldGamePane = gameStateManager.getGamePanes().get(currentPlayer.getPosition());
        oldGamePane.erasePlayer(currentPlayer.getId());

        currentPlayer.setPosition((currentPlayer.getPosition() + diceRoll) % 40);

        GamePane newGamePane = gameStateManager.getGamePanes().get(currentPlayer.getPosition());

        newGamePane.drawPlayer(currentPlayer.getId());
        gameStateManager.logger.addLog("Player " + currentPlayer.getId() + " rolled a " + diceRoll + ". New position: " + currentPlayer.getPosition(),gameStateManager.getLogs());

        return newGamePane;
    }

    public void setPlayerPanelsDirectly(Player currentPlayer){
        for(Pane p : panes)
        {
            p.setDisable(true);
        }

        if(gameStateManager.client.getClientID() == currentPlayer.getId())
        {
            panes.get(currentPlayer.getId()-1).setDisable(false);
        }

    }

    private void togglePlayerPanels(Player currentPlayer) {
        panes.get(gameStateManager.getCurrentPlayerTurn()).setDisable(true);

        gameStateManager.nextPlayerTurn();

        for (int i = 0; i < 4; i++) {
            if (i == 3) {
                gameStateManager.logger.addLog("Player " + currentPlayer.getId() + " is the winner!",gameStateManager.getLogs());
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player " + currentPlayer.getId() + " is the winner!");
                alert.setHeaderText("WINNER");
                alert.showAndWait();
                Stage stage = (Stage) p1btn.getScene().getWindow();
                stage.close();

            }

            if (!gameStateManager.getCurrentPlayer().playing) {
                gameStateManager.nextPlayerTurn();
            } else {
                break;
            }
        }
    }

    private void toggleBuyButton(Player currentPlayer, GamePane newGamePane) {
        if (newGamePane instanceof PropertyPane && !((PropertyPane) newGamePane).getBought()) {
            buyButtons.get(currentPlayer.getId() - 1).setDisable(false);
            placeLabels.get(currentPlayer.getId() - 1).setText(((PropertyPane) newGamePane).getName());
        } else {
            buyButtons.get(currentPlayer.getId() - 1).setDisable(true);
            placeLabels.get(currentPlayer.getId() - 1).setText("");
        }
    }

    private void handlePropertyStep(Player currentPlayer, GamePane newGamePane) {
        Player owner = (((PropertyPane) newGamePane).getOwner());

        PropertyPane ownersPane = ((PropertyPane) newGamePane);

        int toll = (int) (ownersPane.getPrice() * 6);

        owner.setMoney((int) (owner.getMoney() + toll));
        currentPlayer.setMoney(currentPlayer.getMoney() - toll);

        gameStateManager.logger.addLog("Player " + currentPlayer.getId() + " paid " + toll + " to player " + owner.getId() + ".",gameStateManager.getLogs());
    }

    private void handleMiscStep(Player currentPlayer, GamePane newGamePane) {
        MiscPane steppedPane = (MiscPane) newGamePane;

        Random random = new Random();

        switch (steppedPane.getType()) {
            case CHANCE:
                int x = random.nextInt(200);
                currentPlayer.setMoney(currentPlayer.getMoney() + x);
                gameStateManager.logger.addLog("Player " + currentPlayer.getId() + " got " + x + " from the Chance space.",gameStateManager.getLogs());
                break;
            case COMMUNITY_CHEST:
                int y = random.nextInt(500);
                currentPlayer.setMoney(currentPlayer.getMoney() + y);
                gameStateManager.logger.addLog("Player " + currentPlayer.getId() + " got " + y + " from the Community chest.",gameStateManager.getLogs());
                break;
            case INCOME_TAX:
                int z = (int) (currentPlayer.getMoney() * 0.2);
                currentPlayer.setMoney(currentPlayer.getMoney() - z);
                gameStateManager.logger.addLog("Player " + currentPlayer.getId() + " paid " + z + " in taxes.",gameStateManager.getLogs());
                break;
            default:
                throw new IllegalArgumentException("Invalid misc pane type: " + steppedPane.getType());
        }
    }


    public void updateDeedList(Player currentPlayer)
    {
        List<String> deeds = new ArrayList<>();
        for (PropertyPane p : currentPlayer.getTitleDeeds()) {
            deeds.add(p.getName());
        }
        deedLists.get(currentPlayer.getId() - 1).getItems().setAll(deeds);
    }

    private void handleBuyButtonClick(Button button) {

        Player currentPlayer = gameStateManager.getPlayers().get(gameStateManager.getCurrentPlayerTurn());
        PropertyPane currentPane = (PropertyPane) gameStateManager.getGamePanes().get(currentPlayer.getPosition());

        if (currentPlayer.getMoney() >= currentPane.getPrice()) {
            if (currentPane.setBought(currentPlayer)) {
                gameStateManager.logger.addLog("Player " + currentPlayer.getId() + " bought " + currentPane.getName() + " for $" + currentPane.getPrice() + ".",gameStateManager.getLogs());
            }

            updateDeedList(currentPlayer);


        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Cannot Buy Property!");
            alert.setContentText("Not Enough Money!");
            alert.showAndWait();
        }

        buyButtons.get(currentPlayer.getId() - 1).setDisable(true);

        updateMoneyLabels();

        try {
            byte[] serializedData = SerializationController.serializeIntoBytes(this.gameStateManager);
            this.gameStateManager.client.getCSC().getOutputStream().writeInt(serializedData.length);
            this.gameStateManager.client.getCSC().getOutputStream().write(serializedData);
            this.gameStateManager.client.getCSC().getOutputStream().flush();
            System.out.println("Written to out!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private int rollDice() {
        Random random = new Random();
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        return dice1 + dice2;
    }

    public void updateMoneyLabels() {
        for (int i = 0; i < gameStateManager.getPlayers().size(); i++) {
            Player player = gameStateManager.getPlayers().get(i);
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