package hr.algebra.javafxmonopoly;

import hr.algebra.javafxmonopoly.controllers.LogPanelController;
import hr.algebra.javafxmonopoly.controllers.SerializationController;
import hr.algebra.javafxmonopoly.models.GamePane;
import hr.algebra.javafxmonopoly.models.Player;
import hr.algebra.javafxmonopoly.network.client.Client;

import java.util.ArrayList;
import java.util.List;

public class GameStateManager {

    public Client client = new Client(this);
    private List<Player> players;
    transient private List<GamePane> gamePanes;
    private int currentPlayerIndex;
    transient public LogPanelController logger;

    public List<GameStateSerializable> history = new ArrayList<GameStateSerializable>();
    public int historyIndex;

    private List<String> logs;

    public List<String> getLogs()
    {
        return this.logs;
    }

    public int getCurrentPlayerTurn() {
        return this.currentPlayerIndex;
    }

    public void addLogger(LogPanelController logPanelController) {
        this.logger = logPanelController;
    }

    SerializationController serializationController;

    public void setSerializationController(SerializationController serializationController) {
        this.serializationController = serializationController;
    }

    public SerializationController getSerializationController()
    {
        return this.serializationController;
    }


    public void setProperties(GameStateSerializable gameStateSerializable)
    {
        this.players = new ArrayList<Player>(gameStateSerializable.players);
        this.currentPlayerIndex = gameStateSerializable.currentPlayerIndex;
        this.logs = gameStateSerializable.logs;
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

        this.logs = new ArrayList<>();
    }

    public void connectToServer()
    {
        client.connectToServer();
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