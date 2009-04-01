package View;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import Model.RemotePlayer;

public class clientMain {
	
	
	public clientMain(){
		RemotePlayer proxy = null;
		String name = null;
		
		getURL: 
		while(true) { 
			if( name == null ) {
				name = JOptionPane.showInputDialog("Enter the URL for the server", "rmi://xxx.xxx.xxx.xxx/game") ;
				if( name == null ) System.exit(1) ;
			}
			try {
				proxy = (RemotePlayer) Naming.lookup( name ) ;
				break getURL ; }
			catch( java.lang.ClassCastException e ) {
				JOptionPane.showMessageDialog(null, "Could not hook up with server.\nType mismatch") ;
				e.printStackTrace( System.err ); 
				name = null ; }
			catch( java.net.MalformedURLException  e) {
				JOptionPane.showMessageDialog(null, "Could not hook up with server.\nURL not valid.") ;
				e.printStackTrace( System.err ); 
				name = null ; }
			catch( NotBoundException e) {
				JOptionPane.showMessageDialog(null, "Could not hook up with server.\nName not bound on server.") ;
				e.printStackTrace( System.err ); 
				name = null ; }
			catch( RemoteException e) {
				JOptionPane.showMessageDialog(null, "Could not hook up with server.\nNetwork problem.") ;
				e.printStackTrace( System.err );
				name = null ; } 
			catch( Throwable e ) {
				JOptionPane.showMessageDialog(null, "Could not hook up with server.") ;
				e.printStackTrace( System.err );
				name = null ; }	}
		
		
			proxy.setRight(true);
		
	
		
		
		
	
	}

}



