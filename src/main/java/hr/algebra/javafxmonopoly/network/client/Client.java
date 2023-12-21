package hr.algebra.javafxmonopoly.network.client;

import hr.algebra.javafxmonopoly.GameStateManager;
import hr.algebra.javafxmonopoly.JavaFXMonopolyApplication;
import hr.algebra.javafxmonopoly.network.RMIChat.ChatService;
import hr.algebra.javafxmonopoly.network.RMIChat.ChatServiceImpl;
import hr.algebra.javafxmonopoly.network.server.Server;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.*;
import java.io.DataOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    private ClientSideConnection clientSideConnection;

    GameStateManager parent;

    public Client(GameStateManager parent)
    {
        this.parent = parent;
    }

    private byte[] currentData;

    public static ChatService chatService;

    public byte[] getCurrentData()
    {
        return currentData;
    }

    public ClientSideConnection getCSC()
    {
        return this.clientSideConnection;
    }

    private int clientID;

    public int getClientID()
    {
        return this.clientID;
    }

    public class ClientSideConnection {
        private Socket socket;
        private DataInputStream inputStream;
        private DataOutputStream outputStream;

        public DataOutputStream getOutputStream() {
            return outputStream;
        }

        public ClientSideConnection() {
            System.out.println("Creating client side connection...");
            try {
                this.socket = new Socket("localhost", Server.SERVER_PORT);
                this.inputStream = new DataInputStream(socket.getInputStream());
                this.outputStream = new DataOutputStream(this.socket.getOutputStream());
                clientID = inputStream.readInt();
                System.out.println("Client side connection created. (Client " + clientID + ")");

                Thread thread = new Thread(() -> {
                    try {
                        while (true) {
                            int dataSize = inputStream.readInt();
                            byte[] data = new byte[dataSize];

                            int bytesRead = 0;
                            while (bytesRead < dataSize) {
                                bytesRead += inputStream.read(data, bytesRead, dataSize - bytesRead);
                            }

                            currentData = data;
                            Platform.runLater(() -> {
                                parent.getSerializationController().deserializeAndLoad(data);
                                System.out.println("Received data: " + new String(data));
                            });
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                thread.start();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void connectToServer() {

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            chatService = (ChatService) registry.lookup("ChatService");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

        clientSideConnection = new ClientSideConnection();

    }
}