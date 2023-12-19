package hr.algebra.javafxmonopoly.network.client;

import hr.algebra.javafxmonopoly.JavaFXMonopolyApplication;
import hr.algebra.javafxmonopoly.network.server.Server;
import javafx.stage.Stage;

import java.io.*;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private ClientSideConnection clientSideConnection;

    private int clientID;

    public int getClientID()
    {
        return this.clientID;
    }

    private class ClientSideConnection {
        private Socket socket;
        private DataInputStream inputStream;
        private DataOutputStream outputStream;

        public ClientSideConnection() {
            System.out.println("Creating client side connection...");
            try {
                this.socket = new Socket("Localhost", Server.SERVER_PORT);
                this.inputStream = new DataInputStream(socket.getInputStream());
                this.outputStream = new DataOutputStream(this.socket.getOutputStream());
                clientID = inputStream.readInt();
                System.out.println("Client side connection created. (Client " + clientID + ")");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void connectToServer() {
        clientSideConnection = new ClientSideConnection();
    }
}