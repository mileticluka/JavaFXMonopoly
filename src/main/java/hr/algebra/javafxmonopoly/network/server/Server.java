package hr.algebra.javafxmonopoly.network.server;

import hr.algebra.javafxmonopoly.GameStateManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static final int SERVER_PORT = 1000;
    private int numPlayers;
    private ConcurrentHashMap<Integer, Socket> clientSockets;
    private ConcurrentHashMap<Integer, ServerSideConnecton> clientConnections;


    private GameStateManager gameState;


    private ServerSocket serverSocket;

    public Server() {
        System.out.println("Creating server...");
        try {
            this.serverSocket = new ServerSocket(SERVER_PORT);
            this.clientSockets = new ConcurrentHashMap<>();
            this.clientConnections = new ConcurrentHashMap<>();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        this.gameState = new GameStateManager();

        System.out.println("Server Created.");
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");
            while (clientSockets.size() < 4)
            {
                Socket socket = serverSocket.accept();
                numPlayers++;
                clientSockets.put(numPlayers,socket);
                System.out.println("Player " + numPlayers + " connected.");
                ServerSideConnecton serverSideConnecton = new ServerSideConnecton(socket,numPlayers);
                clientConnections.put(numPlayers,serverSideConnecton);
                Thread thread = new Thread(serverSideConnecton);
                thread.start();
            }
            System.out.println("All players connected!");
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private class ServerSideConnecton implements Runnable {
        private Socket socket;
        private DataInputStream inputStream;
        private DataOutputStream outputStream;
        private int playerID;

        public ServerSideConnecton(Socket socket, int playerID)
        {
            this.socket = socket;
            this.playerID = playerID;
            try {
                inputStream = new DataInputStream(socket.getInputStream());
                outputStream = new DataOutputStream(socket.getOutputStream());
            }catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

        public void run() {
            try {
                outputStream.writeInt(playerID);
                outputStream.flush();
                while (true)
                {
                    inputStream.read();
                }
            }catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

    }

    public static void main(String[] args)
    {
        Server server = new Server();
        server.acceptConnections();
    }

}
