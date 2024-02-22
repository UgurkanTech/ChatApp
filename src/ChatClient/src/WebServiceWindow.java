
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class WebServiceWindow {

	private JFrame frmChatAppWeb;
	static JTextPane textPane;
	
	
	public static void ShowWebServiceWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebServiceWindow window = new WebServiceWindow();
					window.frmChatAppWeb.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the application.
	 */
	public WebServiceWindow() {
		initialize();
	}

	
	public static String getHTML(String urlToRead) {
		textPane.setText("");
		insertString("GET " + urlToRead);
		   StringBuilder result = new StringBuilder();
		   try {
			      URL url = new URL(urlToRead);
			      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			      conn.setRequestMethod("GET");
			      try (BufferedReader reader = new BufferedReader(
			                  new InputStreamReader(conn.getInputStream()))) {
			          for (String line; (line = reader.readLine()) != null; ) {
			              result.append(line);
			          }
			      }
			} catch (Exception e) {insertString("Connection problem!");}
		   
		    return result.toString();
	}
	static Document document;
	public static void insertString(String text) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
					document.insertString(document.getLength(), text + "\n", null);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
         });
	}
	
	public static void printJSON(String json) {
		if(json == null ||  json.equals("")) return;
		insertString("Formatted JSON result:");
		//System.out.println("Formatted JSON result:");
		try {
			json = json.replaceAll("\\]|\\[", "");
			
			String[] lines = json.split("},");
			
			for (int i = 0; i < lines.length; i++) {
				String line = (lines[i] + (i == lines.length-1 ? "" : "}")).replaceAll("\"", " ").replaceAll(" : ", "=");
				insertString(line);
				//System.out.println(line);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatAppWeb = new JFrame();
		frmChatAppWeb.getContentPane().setBackground(Color.DARK_GRAY);
		frmChatAppWeb.setTitle("Chat App Web Service Client");
		frmChatAppWeb.setBounds(100, 100, 764, 348);
		frmChatAppWeb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmChatAppWeb.getContentPane().setLayout(null);
		frmChatAppWeb.setResizable(false);
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setBackground(new Color(46, 139, 87));
		panel_1.setBounds(10, 11, 728, 251);
		frmChatAppWeb.getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		frmChatAppWeb.setLocationRelativeTo(null);
		
		
		textPane = new JTextPane();
		textPane.setForeground(Color.WHITE);
		textPane.setEditable(false);
		textPane.setBackground(Color.BLACK);
	
		
		JScrollPane jsp = new JScrollPane(textPane);
		panel_1.add(jsp, BorderLayout.CENTER);
		
		JGradientButton grdntbtnGetChatLogs = new JGradientButton();
		grdntbtnGetChatLogs.setForeground(new Color(255, 255, 240));
		grdntbtnGetChatLogs.setBackground(new Color(100, 149, 237));
		grdntbtnGetChatLogs.setFont(new Font("Tahoma", Font.PLAIN, 11));
		grdntbtnGetChatLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = String.format("http://%s:8080/logs/chat", NetworkManager.serverIP);
				String res = getHTML(str);
				printJSON(res);
			}
		});
		grdntbtnGetChatLogs.setText("Get Chat Logs");
		grdntbtnGetChatLogs.setBounds(10, 273, 123, 20);
		frmChatAppWeb.getContentPane().add(grdntbtnGetChatLogs);
		
		JGradientButton grdntbtnGetUserActivities = new JGradientButton();
		grdntbtnGetUserActivities.setForeground(new Color(255, 255, 240));
		grdntbtnGetUserActivities.setBackground(new Color(100, 149, 237));
		grdntbtnGetUserActivities.setFont(new Font("Tahoma", Font.PLAIN, 11));
		grdntbtnGetUserActivities.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = String.format("http://%s:8080/logs/activity", NetworkManager.serverIP);
				String res = getHTML(str);
				printJSON(res);
			}
		});
		grdntbtnGetUserActivities.setText("Get Activity Logs");
		grdntbtnGetUserActivities.setBounds(143, 273, 123, 20);
		frmChatAppWeb.getContentPane().add(grdntbtnGetUserActivities);
		
		JGradientButton grdntbtnGetEmployees = new JGradientButton();
		grdntbtnGetEmployees.setForeground(new Color(255, 255, 240));
		grdntbtnGetEmployees.setBackground(new Color(85, 107, 47));
		grdntbtnGetEmployees.setFont(new Font("Tahoma", Font.PLAIN, 11));
		grdntbtnGetEmployees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = String.format("http://%s:8080/data/employees", NetworkManager.serverIP);
				String res = getHTML(str);
				printJSON(res);
			}
		});
		grdntbtnGetEmployees.setText("Get Employees");
		grdntbtnGetEmployees.setBounds(276, 273, 124, 20);
		frmChatAppWeb.getContentPane().add(grdntbtnGetEmployees);
		
		JGradientButton grdntbtnGetRegisters = new JGradientButton();
		grdntbtnGetRegisters.setForeground(new Color(255, 255, 240));
		grdntbtnGetRegisters.setBackground(new Color(85, 107, 47));
		grdntbtnGetRegisters.setFont(new Font("Tahoma", Font.PLAIN, 11));
		grdntbtnGetRegisters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = String.format("http://%s:8080/data/registers", NetworkManager.serverIP);
				String res = getHTML(str);
				printJSON(res);
			}
		});
		grdntbtnGetRegisters.setText("Get Registers");
		grdntbtnGetRegisters.setBounds(410, 273, 124, 20);
		frmChatAppWeb.getContentPane().add(grdntbtnGetRegisters);
		
	
		
		JGradientButton grdntbtnClear = new JGradientButton();
		grdntbtnClear.setForeground(new Color(255, 255, 240));
		grdntbtnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.setText("");
			}
		});
		grdntbtnClear.setBackground(new Color(205, 92, 92));
		grdntbtnClear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		grdntbtnClear.setText("Clear");
		grdntbtnClear.setBounds(678, 273, 60, 20);
		frmChatAppWeb.getContentPane().add(grdntbtnClear);
		
		JGradientButton grdntbtnAddEmployee = new JGradientButton();
		grdntbtnAddEmployee.setForeground(new Color(255, 255, 240));
		grdntbtnAddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewEmployee.showPanel();
			}
		});
		grdntbtnAddEmployee.setText("Add Employee");
		grdntbtnAddEmployee.setFont(new Font("Tahoma", Font.PLAIN, 11));
		grdntbtnAddEmployee.setBackground(new Color(147, 112, 219));
		grdntbtnAddEmployee.setBounds(544, 273, 124, 20);
		frmChatAppWeb.getContentPane().add(grdntbtnAddEmployee);
		
		
		document = textPane.getDocument();

		
	}
}
