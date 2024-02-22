package chatserver;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A TCP Server
 * 
 * All rights reserved.
 * 
 * @author Uğurkan Hoşgör
 *
 */
public class ServerTCP extends Thread{

	//connectivity
	public boolean reconnectOnError = true;
	public boolean multipleClients = true;
	public int clientLimit = 128;
	
	
	//communication settings
	public boolean compressData = false;
	public boolean encryptData = false;
	private final boolean DEBUG = false;
	
	public ServerSocket serversocket;
	
	public static boolean stop = false;
	
	public static ArrayList<ReceiverTCP> clients = new ArrayList<ReceiverTCP>();
	public static ArrayList<SenderTCP> clientSenders = new ArrayList<SenderTCP>();

	private final int serverSecret = 1337;

	private boolean isClientExist(int id) {
		for (int j = 0; j < clients.size(); j++) {
			if(clients.get(j).getClientID() == id) 
				return true;
		}
		return false;
	}
	
	private int getNewClientID() {
		for (int i = 0; i < 250; i++) {
			if (!isClientExist(i)) {
				return i;
			}
		}
		return 0;
	}
	
	
	public ServerTCP() {
		startServer();
	}
	
	private void startServer() {
		start();
	}
	
	@Override
	public void run() {
		
		encryptData = ServerWindow.chckbxNewCheckBox.isSelected();
		compressData = ServerWindow.chckbxCompression.isSelected();
		
		ServerWindow.chckbxNewCheckBox.setEnabled(false);
		ServerWindow.chckbxCompression.setEnabled(false);
		
		int port = 5555;
		
		try {
			int p = Integer.parseInt(ServerWindow.textField.getText());
			port = p;
		} catch (Exception e) {
			System.out.println("Error: Invalid server port. Using default port:5555");
		}
		
		
		try {
			serversocket = new ServerSocket(port);
		} catch (IOException e1) {e1.printStackTrace();}
		System.out.println("Server is started! - Port: " + serversocket.getLocalPort());
	
		do {
			
			try {
					
				while ((clients.size() >= clientLimit && clientLimit != -1) || clients.size() >= 255) {
					Thread.sleep(100);
					for (int i = 0; i < clients.size(); i++) {
						if (!clients.get(i).isAlive()) {
							clients.remove(i);
							clientSenders.remove(i);
						}
					}
				}

				 
				System.out.println("Server is waiting for connections...");	
				Socket socket = serversocket.accept();

				int clientID = getNewClientID();
				
				socket.getOutputStream().write(clientID); //assign an id
				
				//send communication protocol
				if (!compressData && !encryptData) {
					socket.getOutputStream().write(0);
				}
				else if (compressData && !encryptData) {
					socket.getOutputStream().write(1);
				}
				else if (!compressData && encryptData) {
					socket.getOutputStream().write(2);	
				}
				else if (compressData && encryptData) {
					socket.getOutputStream().write(3);
				}
				
					
				System.out.println("A client connected!" + " --- (Client ID: " + clientID + ")" + " --- IP: " + socket.getRemoteSocketAddress());
				
				
				ReceiverTCP receiver =  new ReceiverTCP(socket, clientID, serverSecret, compressData, encryptData);
				SenderTCP sender =  new SenderTCP(socket, clientID, serverSecret, compressData, encryptData);
				
				clients.add(receiver); //client id
				clientSenders.add(sender);
				
				receiver.setName("Receiver-" + clientID);
				sender.setName("Sender-" + clientID);
				
				ServerWindow.updateClientList();
				
				
				//They will finish if connection fails
				if (!multipleClients) {
					receiver.join();
				}
					
			} catch (Exception e) {if (DEBUG) e.printStackTrace(); else System.err.println("Server socket closed!");}
			
			//Delay for reconnecting..
			
			try {Thread.sleep(100);} 
			catch (InterruptedException e) {}
			catch (Exception e) {e.printStackTrace();}
			if(stop) break;
		} while (reconnectOnError || multipleClients);
		
	}
	
	
}
