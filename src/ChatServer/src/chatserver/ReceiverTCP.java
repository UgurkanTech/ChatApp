package chatserver;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReceiverTCP extends Thread{
		private Socket socket;
		private InputStream input;
		private DataInputStream dataInput;
		private int clientID;
		
		private boolean compressData;
		private boolean encryptData;
		private final boolean DEBUG = false;
		
		private int encryptKey;
		
		public static boolean stop = false;
		
		public int getClientID() {
			return clientID;
		}
		
		public ReceiverTCP(Socket s, int clientID, int serverSecret, boolean compress, boolean encrypt) {
			this.socket = s;
			this.clientID = clientID;
			this.compressData = compress;
			this.encryptData = encrypt;
			encryptKey = clientID * serverSecret;
			start();
		}
		@Override
		public void run() {
			try {
				input = socket.getInputStream();
				dataInput = new DataInputStream(input);
			} catch (Exception e) {e.printStackTrace();}
			//Data loop
			while (true) {
				try {
					int bufferSize = dataInput.readInt();
					byte[] buffer = new byte[bufferSize];
					dataInput.read(buffer);
					
					
					
					if (encryptData) {buffer = ChatUtils.AES.decrypt(buffer, encryptKey + "");}
					
					if (compressData) {buffer = ChatUtils.Compressor.Uncompress(buffer);}
					
					String text = new String(buffer);
					
					System.out.println("Client " + clientID + " sent: " + text + " --- (" + bufferSize + " bytes)");
					//CommandExecutioner.execute(text);
					
					NetworkManagerServer.handleCommand(text, clientID);
					
				} catch (IOException e1) {
					if (DEBUG) 
						e1.printStackTrace(); 
					else {
						System.err.println("A client disconnected! --- (Client ID: " + clientID + ") --- IP: " + socket.getRemoteSocketAddress());
						for (int i = 0; i < ServerTCP.clientSenders.size(); i++) {
							if (ServerTCP.clientSenders.get(i).clientID == clientID) {
								ServerTCP.clientSenders.remove(i);
								ServerTCP.clients.remove(i);

								synchronized ("ou") {
									for (String key: NetworkManagerServer.onlineUsers.keySet()) {
									    String value = NetworkManagerServer.onlineUsers.get(key);
									    int id = Integer.parseInt(value.split(";")[0]);
									    if(id == clientID) {
									    	NetworkManagerServer.leaveMessage(key);
									    	NetworkManagerServer.logActivity(key, "Disconnected!");
									    	NetworkManagerServer.onlineUsers.remove(key);
									    	NetworkManagerServer.sendUpdateOnlineUsers();
									    	break;
									    }
									    	
									}
								}
								
								break;
							}
						}
						ServerWindow.updateClientList();
						}
					break;
				}
				if(stop) break;
				//try {Thread.sleep(1000);} catch (Exception e) {e.printStackTrace();}
			}
		}
	}