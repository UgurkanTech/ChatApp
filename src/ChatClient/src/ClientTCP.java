import java.io.IOException;
import java.net.Socket;

/**
 * A TCP Client
 * 
 * All rights reserved.
 * 
 * @author Uğurkan Hoşgör
 *
 */

public class ClientTCP extends Thread{

	public boolean reconnectOnError = true;
	
	private final boolean DEBUG = false;
	
	public static boolean connected = false;
	
	private int clientID;

	private final int serverSecret = 1337;
	private boolean compressData;
	private boolean encryptData;
	
	String ip = "";
	int port = 0;
	
	
	public ClientTCP(String ip, int port) {
		this.ip = ip;
		this.port = port;
		startClient();
	}
	
	private void startClient() {
		start();
	}
	
	ReceiverTCP receiver;
	SenderTCP sender;
	Socket socket;
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		do{
			try {
					
				SenderTCP.stop = false;
				ReceiverTCP.stop = false;
				Thread.sleep(100);
				System.out.println("Client trying to connect... (" + ip + ":" + port + ")");

				socket = new Socket(ip, port);

				clientID = socket.getInputStream().read(); //get an id

				int protocol = socket.getInputStream().read();
				
				//set protocol
				if (protocol == 0) {compressData = false; encryptData = false;}
				else if (protocol == 1) {compressData = true; encryptData = false;}
				else if (protocol == 2) {compressData = false; encryptData = true;}
				else if (protocol == 3) {compressData = true; encryptData = true;}
				
				NetworkManager.sendQueue.clear();
				
				System.out.println("Connection established! - My Username: User" + clientID);
				NetworkManager.userID = clientID;
				connected = true;
				receiver =  new ReceiverTCP(socket, clientID, serverSecret, compressData, encryptData);
				sender =  new SenderTCP(socket, clientID, serverSecret, compressData, encryptData);
				
				receiver.setName("Receiver-" + clientID);
				sender.setName("Sender-" + clientID);
				
				
				if(NetworkManager.lastLoginState != null)
					NetworkManager.sendQueue.add(NetworkManager.lastLoginState);
				
				//They will finish if connection fails
				receiver.join();
				connected = false;
				SenderTCP.stop = true;
				ReceiverTCP.stop = true;
				receiver.stop();
				sender.stop();
				
			} catch (Exception e) {
				if (DEBUG) 
					e.printStackTrace();
				else 
					System.err.println("Client couldn't connect.");
					SenderTCP.stop = true;
					ReceiverTCP.stop = true;
					try {
						if(socket != null) socket.close();} catch (IOException e1) {e1.printStackTrace();}
				}
			
			//Delay for reconnecting..
			try {Thread.sleep(500);} catch (Exception e) {e.printStackTrace();}

		}
		while(reconnectOnError);
	}
	
	
}
