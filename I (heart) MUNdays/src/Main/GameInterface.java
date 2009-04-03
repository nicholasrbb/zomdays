package Main;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Model.Player;

public interface GameInterface extends Remote{
	
	
	

	public Player getPlayer() throws RemoteException;
}
