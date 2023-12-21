package hr.algebra.javafxmonopoly.network.RMIChat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatService extends Remote {
    void sendMessage(String clientMessage) throws RemoteException;
    List<String> getMessages() throws  RemoteException;
}
