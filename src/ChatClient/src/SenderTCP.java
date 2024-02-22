import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SenderTCP extends Thread{
		private Socket socket;
		private OutputStream output;
		private DataOutputStream dataout;
		private int clientID;
		private int encryptKey;
		
		//communication settings
		public boolean compressData = true;
		public boolean encryptData = true;
		private final boolean DEBUG = true;
		
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
					
					String text = NetworkManager.sendQueue.poll();
		
					
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
				try {Thread.sleep(20);} catch (Exception e) {e.printStackTrace();}
				if(stop) break;
			}
		}
	}