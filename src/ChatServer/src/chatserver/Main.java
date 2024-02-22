package chatserver;
import java.awt.EventQueue;

//Server
public class Main {

	public static void main(String[] args) {
		new Main();

	}
	
	public Main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow window = new ServerWindow();
					window.frmChatAppServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
