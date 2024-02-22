package chatserver;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class SenderTCP extends Thread{
		public Socket socket;
		private OutputStream output;
		private DataOutputStream dataout;
		public int clientID;
		private int encryptKey;
		
		//communication settings
		public boolean compressData = true;
		public boolean encryptData = true;
		private final boolean DEBUG = false;
		
		public Queue<String> sendQueue = new LinkedList<String>();
		
		public static boolean stop = false;
		
		public SenderTCP(Socket s, int clientID, int serverSecret, boolean compress, boolean encrypt) {
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
				output = socket.getOutputStream();
				dataout = new DataOutputStream(output);
			} catch (Exception e) {e.printStackTrace();}
			//Data loop
			while (true) {
				try {

					String text = sendQueue.poll();
		
					if (text != null) {
						byte[] data = text.getBytes("UTF-8");
						
						if (compressData) {data = ChatUtils.Compressor.Compress(data);}
						
						if (encryptData) {data = ChatUtils.AES.encrypt(data, encryptKey + "");}
						
						dataout.writeInt(data.length);
						dataout.write(data);
					}
					

					
				} catch (IOException e1) {
					if (DEBUG) e1.printStackTrace(); else System.err.println("Data writing failed! - Client: " + clientID);
					break;
				}
				if(stop) break;
				try {Thread.sleep(20);} catch (Exception e) {e.printStackTrace();}
			}
		}
	}