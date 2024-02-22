import java.awt.EventQueue;


//Client
public class Main {
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					MainWindow window = new MainWindow();
					//MainWindow.frmChatApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
