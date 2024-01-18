package hr.algebra.javafxmonopoly;

import hr.algebra.javafxmonopoly.controllers.LogPanelController;
import hr.algebra.javafxmonopoly.models.GamePane;
import hr.algebra.javafxmonopoly.models.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameStateSerializable implements Serializable {
    public List<Player> players;
    public int currentPlayerIndex;
    public List<String> logs;

    public void setProperties(GameStateManager gameStateManager)
    {
        this.players = new ArrayList<Player>(gameStateManager.getPlayers());
        this.currentPlayerIndex = gameStateManager.getCurrentPlayerTurn();
        this.logs = new ArrayList<>(gameStateManager.getLogs());
    }
}
