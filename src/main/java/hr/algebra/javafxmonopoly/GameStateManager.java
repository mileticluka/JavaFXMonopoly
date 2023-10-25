package hr.algebra.javafxmonopoly;

import hr.algebra.javafxmonopoly.controllers.LogPanelController;
import hr.algebra.javafxmonopoly.models.GamePane;
import hr.algebra.javafxmonopoly.models.Player;
import hr.algebra.javafxmonopoly.models.PropertyPane;

import java.util.ArrayList;
import java.util.List;

public class GameStateManager {

    private final List<Player> players;
    private List<GamePane> gamePanes;
    private int currentPlayerIndex;

    public LogPanelController logger;

    public int getCurrentPlayerTurn() {
        return this.currentPlayerIndex;
    }

    public void addLogger(LogPanelController logPanelController) {
        this.logger = logPanelController;
    }

    public GameStateManager() {
        // Initialize players
        players = new ArrayList<>();
        players.add(new Player(1, 1000));
        players.add(new Player(2, 1000));
        players.add(new Player(3, 1000));
        players.add(new Player(4, 1000));

        // Set the initial player
        currentPlayerIndex = 0;
    }

    public void setGamePanes(List<GamePane> gamePanes) {
        this.gamePanes = gamePanes;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayerTurn() {

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public List<GamePane> getGamePanes() {
        return gamePanes;
    }
}