package chatserver;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NetworkManagerServer {

	
	//mail, id;name;color
	public static Map<String, String> onlineUsers = new HashMap<String, String>();
	
	//public static Queue<String> sendQueue = new LinkedList<String>();
	
	public NetworkManagerServer() {
		
	}
	
	
	public static void sendToAClient(int clientID, String text) {
		for (int i = 0; i < ServerTCP.clientSenders.size(); i++) {
			if (ServerTCP.clientSenders.get(i).clientID == clientID) {
				ServerTCP.clientSenders.get(i).sendQueue.add(text);
				break;
			}
		}
	}
	public static void sendToAllClients(String text) {
		for (int i = 0; i < ServerTCP.clientSenders.size(); i++) {
			ServerTCP.clientSenders.get(i).sendQueue.add(text);
		}
	}
	
	public static void sendUpdateOnlineUsers() {
		String data = "users@@@";
		
		for (String key: onlineUsers.keySet()) {
		    String value = onlineUsers.get(key);
		    data += value.split(";")[1] + "," + key + ";";
		}

		data = data.substring(0, data.length()-1);
		sendToAllClients(data);
		
		ServerWindow.updateClientList();
		//System.out.println(data);
	}
	
	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
	static LocalDateTime now;
	
	public static void handleCommand(String commandText, int clientID) {
		String[] args = commandText.split("@@@");
		
		String cmd = args[0];
		
		String[] cmdArgs = args[1].split(";");
		
		
		
		now = LocalDateTime.now();  
		    
		
		String time = dtf.format(now) ;
		
		switch (cmd) {
		case "register": 
			handleRegister(cmdArgs, clientID);
			break;
		case "login": 
			handleLogin(cmdArgs, clientID);
			break;
		case "private": 
			handlePrivateMessage(cmdArgs, time);
			break;
		case "global": 
			handleGlobalMessage(cmdArgs, time);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + cmd);
		}
		
		
	}
	
	public static void handleGlobalMessage(String[] cmdArgs, String time) {
		
		sendToAllClients("global@@@" + time + ";" + onlineUsers.get(cmdArgs[0]).split(";")[1] + ";" + cmdArgs[1] + ";" + onlineUsers.get(cmdArgs[0]).split(";")[2]);
		DatabaseConnection.logChatMessage(new ChatMessage(time, cmdArgs[0], "Global", cmdArgs[1]));
	
	}
	
	
	public static void handlePrivateMessage(String[] cmdArgs, String time) {
		//Receiver
		String text = "private@@@" + time + ";" + onlineUsers.get(cmdArgs[0]).split(";")[1] + ";" + cmdArgs[2] + ";" + onlineUsers.get(cmdArgs[0]).split(";")[2];
		sendToAClient(Integer.parseInt(onlineUsers.get(cmdArgs[1]).split(";")[0]), text);
		
		//Owner
		
		String text2 = "privateSent@@@" + time + ";" + onlineUsers.get(cmdArgs[1]).split(";")[1] + ";" + cmdArgs[2] + ";" + onlineUsers.get(cmdArgs[0]).split(";")[2];
		sendToAClient(Integer.parseInt(onlineUsers.get(cmdArgs[0]).split(";")[0]), text2);
		
		DatabaseConnection.logChatMessage(new ChatMessage(time, cmdArgs[0], cmdArgs[1], cmdArgs[2]));
	}
	
	
	public static void handleRegister(String[] args, int id) {
		
		String res = DatabaseConnection.registerUser(args[0], args[1]);
		String cmd = "auth@@@" + res;
		sendToAClient(id, cmd);
		if (res.startsWith("1")) {
			Employee[] e = DatabaseConnection.getEmployees();
			for (int i = 0; i < e.length; i++) {
				if (e[i].email.equals(args[0])) {
					onlineUsers.put(args[0], id + ";" + e[i].name + ";" + randomColor());
					loginMessage(args[0]);
				}
			}
			
			
		}
		sendUpdateOnlineUsers();
		checkPermission(args[0], id);
		logActivity(args[0], res.split(";")[1]);
	}
	
	
	public static void checkPermission(String email, int id) {
		boolean isAdmin = DatabaseConnection.isAdmin(email);
		
		if (isAdmin) {
			now = LocalDateTime.now();  
			String time = dtf.format(now);
			
			sendToAClient(id, "cmd@@@admin;true");
			sendToAClient(id, "global@@@" + time + ";" + "[Server]" + ";" + "You are an admin!" + ";" + "255,0,0");
		}
	}
	
	static Random rand = new Random();
	public static String randomColor() {
		
		int r = (int) (5*(Math.floor(Math.abs(rand.nextInt(100, 255)/5))));;
		int g = (int) (5*(Math.floor(Math.abs(rand.nextInt(100, 255)/5))));;
		int b = (int) (5*(Math.floor(Math.abs(rand.nextInt(100, 255)/5))));;
		Color c = new Color(r, g, b);

		String res = c.getRed() + "," + c.getGreen() + "," + c.getBlue();
		return res;
	}
	
	public static void logActivity(String userMail, String activity) {
		now = LocalDateTime.now();  
		String time = dtf.format(now);
		
		DatabaseConnection.logChatActivities(new Activity(time, userMail, activity));
	}
	
	
	public static void loginMessage(String userMail) {
		
		now = LocalDateTime.now();  

		String time = dtf.format(now);
		
		sendToAllClients("global@@@" + time + ";" + "[Server]" + ";" + onlineUsers.get(userMail).split(";")[1] + " joined chat!" + ";" + "255,0,0");
		
	}
	
	public static void leaveMessage(String userMail) {
		
		now = LocalDateTime.now();  

		String time = dtf.format(now);
		
		sendToAllClients("global@@@" + time + ";" + "[Server]" + ";" + onlineUsers.get(userMail).split(";")[1] + " left chat!" + ";" + "255,0,0");
		
	}
	
	public static void handleLogin(String[] args, int id) {
		
		String res = DatabaseConnection.loginUser(args[0], args[1]);
		boolean joined = false;
		
		if (res.startsWith("1")) {
			Employee[] e = DatabaseConnection.getEmployees();
			for (int i = 0; i < e.length; i++) {
				if (e[i].email.equals(args[0])) {
					
					if (onlineUsers.containsKey(args[0])) {
						res = "0;User is already online!";
					}
					else {
						onlineUsers.put(args[0], id + ";" + e[i].name + ";" + randomColor());
						joined = true;
					}
					
					
				}
			}
		}
		String cmd = "auth@@@" + res;
		sendToAClient(id, cmd);
		if(joined) loginMessage(args[0]);
		sendUpdateOnlineUsers();
		checkPermission(args[0], id);
		logActivity(args[0], res.split(";")[1]);
	}
}
