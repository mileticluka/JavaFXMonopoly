package hr.algebra.javafxmonopoly.network.RMIChat;

import hr.algebra.javafxmonopoly.GameStateManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl implements ChatService {

    List<String> chatMessages;

    public ChatServiceImpl() throws RemoteException {
        chatMessages = new ArrayList<>();
    }

    @Override
    public void sendMessage(String clientMessage) throws RemoteException {
        chatMessages.add(clientMessage);
    }

    @Override
    public List<String> getMessages() throws RemoteException {
        return chatMessages;
    }

}
