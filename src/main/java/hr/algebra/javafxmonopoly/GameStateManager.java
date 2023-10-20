package hr.algebra.javafxmonopoly;

import java.util.ArrayList;
import java.util.List;

public class GameStateManager {

    private List<Player> players;
    private int currentPlayerIndex;

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

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayerTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}