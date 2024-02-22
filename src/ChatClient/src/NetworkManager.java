import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.swing.JOptionPane;

public class NetworkManager {
	
	public static Map<String, String> onlineUsers = new HashMap<String, String>();
	
	public static String username;
	public static String clientEmail;
	public static String serverIP;
	public static boolean isAdmin = false;
	
	public static int userID;
	public static Queue<String> sendQueue = new LinkedList<String>();
	
	public NetworkManager() {
		
	}
	
	public static void connect() {
		String add = ConfigManager.getIP();
		String[] parts = add.split(":");
		String ip = parts[0];
		int port = 777;
		
		try {
			port = Integer.parseInt(parts[1]);
		} catch (Exception e) {System.out.println("Invalid IP!"); ip = "127.0.0.1";}
		serverIP = ip;
		new ClientTCP(ip, port);
	}
	
	
	public static void handleCommand(String commandText) {
		String[] args = commandText.split("@@@");
		
		String cmd = args[0];
		
		String[] cmdArgs = args[1].split(";");
		
		switch (cmd) {
		case "users": 
			updateUsers(cmdArgs);
			break;
		case "private": 
			printPrivateMessage(cmdArgs);
			break;
		case "privateSent": 
			printPrivateSentMessage(cmdArgs);
			break;
		case "global": 
			printGlobalMessage(cmdArgs);
			break;
		case "auth": 
			handleAuthStatus(cmdArgs[0], cmdArgs[1]);
			break;
		case "cmd": 
			handleCmd(cmdArgs[0], cmdArgs[1]);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + cmd);
		}
		
		
	}
	
	public static void handleCmd(String cmd, String arg) {
		boolean flag = Boolean.parseBoolean(arg);
		switch (cmd) {
		case "admin":
			isAdmin = flag;
			MainWindow.grdntbtnS.setVisible(isAdmin);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + cmd);
		}
	}
	
	
	public static void printPrivateMessage(String[] cmdArgs) {
		System.out.println(cmdArgs[0] + " : " + cmdArgs[1] + ":(private message) :->: " + cmdArgs[2] + "@@@" + cmdArgs[3]);
		SoundManager.playDefaultNotificationSound();
		
	}
	
	public static void printPrivateSentMessage(String[] cmdArgs) {
		System.out.println(cmdArgs[0] + " : " + username + ": (sent to " + cmdArgs[1]  + ") :->: " + cmdArgs[2] + "@@@" + cmdArgs[3]);
		
	}
	
	public static void printGlobalMessage(String[] cmdArgs) {
		System.out.println(cmdArgs[0] + " : " + cmdArgs[1] + "::->: " + cmdArgs[2]+ "@@@" + cmdArgs[3]);
		
		if (!cmdArgs[1].equals(username))
			SoundManager.playDefaultNotificationSound();
		
	}
	
	
	public static void handleAuthStatus(String status, String message) {
		
		
		if (status.equals("1")) {
			MainWindow.dialog.setVisible(false);
			MainWindow.frmChatApp.setVisible(true);
			MainWindow.textPane.setText(message + "\n");
			MainWindow.textArea.requestFocus();
		}
		else {
			
			if (MainWindow.frmChatApp.isVisible()) {
				MainWindow.frmChatApp.setVisible(false);
				MainWindow.dialog.setVisible(true);
			}
			
			JOptionPane.showMessageDialog(null, message);
		}
		
	}
	
	public static String lastLoginState = null;
	
	public static void loginRequest(String mail, String pass) {
		String code = "login@@@" + mail + ";" + pass;
		lastLoginState = code;
		sendQueue.add(code);
		clientEmail = mail;
	}
	
	public static void registerRequest(String mail, String pass) {
		String code = "register@@@" + mail + ";" + pass;
		lastLoginState = code.replace("register", "login");
		sendQueue.add(code);
		clientEmail = mail;
	}
		
	
	public static void sendMessage(String receiver, String content) {
		if (receiver.equals("Global")) {
			String code = "global@@@" + clientEmail + ";" + content;
			sendQueue.add(code);
		}
		else {
			String code = "private@@@" + clientEmail + ";" + onlineUsers.get(receiver) + ";" + content;
			sendQueue.add(code);
		}
	}
	
	
	private static void updateUsers(String[] users) {
		MainWindow.model.clear();
		onlineUsers.clear();
		MainWindow.model.addElement("Global");
		for (int i = 0; i < users.length; i++) {
			String[] person = users[i].split(",");
			
			String name = person[0];
			String mail = person[1];
			
			if (mail.equals(clientEmail)) {
				username = name;
				name += " (You)";
				
			}
			
			onlineUsers.put(name, mail);
			MainWindow.model.addElement(name);
		}
		MainWindow.list.setSelectedValue(MainWindow.lastSelectedPerson, true);
		
		if (MainWindow.list.isSelectionEmpty()) 
			MainWindow.list.setSelectedValue("Global", true);
		
	}
}
