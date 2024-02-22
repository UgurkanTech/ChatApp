package chatserver;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import javax.swing.border.EtchedBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.LineBorder;

import chatserver.webservice.WebService;

import javax.swing.JTextPane;
import java.awt.Font;
import java.io.PrintStream;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.border.MatteBorder;

public class ServerWindow {

	JFrame frmChatAppServer;
	
	public static ServerTCP serverTCP = null;

	public static JScrollPane jsp;
	public static JCheckBox chckbxNewCheckBox;
	public static JCheckBox chckbxCompression;
	/**
	 * Create the application.
	 */
	public ServerWindow() {
		new DatabaseConnection();
		DatabaseConnection.ConnectDatabase();
		initialize();
		new NetworkManagerServer();
	}
	public static DefaultListModel<String> model = new DefaultListModel<>();
	public static JTextField textField;
	public static JTextField textField_1;
	
	
	public static void updateClientList() {
		model.clear();
		synchronized ("ou") {
			for (String key: NetworkManagerServer.onlineUsers.keySet()) {
			    String value = NetworkManagerServer.onlineUsers.get(key);
			    model.addElement(value.split(";")[1]);
			}
		}

	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatAppServer = new JFrame();
		frmChatAppServer.getContentPane().setBackground(Color.DARK_GRAY);
		frmChatAppServer.setTitle("Chat App Server");
		frmChatAppServer.setResizable(false);
		frmChatAppServer.setBounds(100, 100, 620, 362);
		frmChatAppServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatAppServer.getContentPane().setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBackground(Color.BLACK);
		panel_3.setBounds(480, 11, 114, 275);
		frmChatAppServer.getContentPane().add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("         Online Users");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setForeground(new Color(50, 205, 50));
		panel_3.add(lblNewLabel, BorderLayout.NORTH);
		
		
		JList<String> list = new JList<String>(model);
		list.setBackground(new Color(143, 188, 143));
		list.setSelectedIndex(0);
		list.setBorder(new LineBorder(new Color(50, 205, 50), 1, true));
		panel_3.add(list, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setBackground(new Color(46, 139, 87));
		panel_1.setBounds(10, 11, 460, 239);
		frmChatAppServer.getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JTextPane textPane = new JTextPane();
		jsp = new JScrollPane(textPane);
		textPane.setForeground(Color.WHITE);
		textPane.setEditable(false);
		textPane.setBackground(new Color(0, 0, 0));
		panel_1.add(jsp, BorderLayout.CENTER);
		
		JGradientButton grdntbtnStartServer = new JGradientButton();
		grdntbtnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (serverTCP == null) {
					ServerTCP.stop = false;
					serverTCP = new ServerTCP();
					grdntbtnStartServer.setEnabled(false);
					textField.setEditable(false);
				}
				else {
					System.err.println("Server is already running!");
				}
				
			}
		});
		grdntbtnStartServer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		grdntbtnStartServer.setBackground(new Color(100, 149, 237));
		grdntbtnStartServer.setText("Start Server");
		grdntbtnStartServer.setBounds(10, 261, 194, 25);
		frmChatAppServer.getContentPane().add(grdntbtnStartServer);
		
		JGradientButton grdntbtnStartWebservice = new JGradientButton();
		grdntbtnStartWebservice.setForeground(new Color(0, 0, 0));

		grdntbtnStartWebservice.setFont(new Font("Tahoma", Font.PLAIN, 11));
		grdntbtnStartWebservice.setBackground(new Color(100, 149, 237));
		grdntbtnStartWebservice.setText("Start Webservice");
		grdntbtnStartWebservice.setBounds(347, 261, 123, 25);
		frmChatAppServer.getContentPane().add(grdntbtnStartWebservice);
		
		
		URL resource = getClass().getResource("/Icon.png");
		ImageIcon tmp = new ImageIcon(resource);
		//ImageIcon tmp = new ImageIcon(ImageIO.read(new File("./Icon.png")));
		frmChatAppServer.setIconImage(tmp.getImage());
		

		TextPaneOutputStream os= new TextPaneOutputStream(textPane, "");
		
		chckbxNewCheckBox = new JCheckBox("Encryption");
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.setForeground(Color.WHITE);
		chckbxNewCheckBox.setBackground(Color.DARK_GRAY);
		chckbxNewCheckBox.setBounds(20, 293, 77, 23);
		frmChatAppServer.getContentPane().add(chckbxNewCheckBox);
		
		chckbxCompression = new JCheckBox("Compression");
		chckbxCompression.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxCompression.setSelected(true);
		chckbxCompression.setForeground(Color.WHITE);
		chckbxCompression.setBackground(Color.DARK_GRAY);
		chckbxCompression.setBounds(112, 293, 92, 23);
		frmChatAppServer.getContentPane().add(chckbxCompression);
		
		JLabel lblNewLabel_1 = new JLabel("ChatApp Server v1.0");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBackground(Color.DARK_GRAY);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(441, 297, 153, 14);
		frmChatAppServer.getContentPane().add(lblNewLabel_1);
		
		JGradientButton grdntbtnStop = new JGradientButton();
		grdntbtnStop.setBackground(new Color(205, 92, 92));
		grdntbtnStop.setForeground(new Color(0, 0, 0));

		grdntbtnStop.setEnabled(false);
		grdntbtnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WebService.stopWebService();
				grdntbtnStartWebservice.setEnabled(true);
				grdntbtnStop.setEnabled(false);
				textField_1.setEditable(true);
			}
		});
		grdntbtnStartWebservice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WebService.startWebService();
				grdntbtnStartWebservice.setEnabled(false);
				grdntbtnStop.setEnabled(true);
				textField_1.setEditable(false);
			}
		});

		grdntbtnStop.setText("Stop Webservice");
		grdntbtnStop.setFont(new Font("Tahoma", Font.PLAIN, 11));

		grdntbtnStop.setBounds(347, 292, 123, 25);
		frmChatAppServer.getContentPane().add(grdntbtnStop);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField.setText("777");
		textField.setBounds(228, 294, 40, 20);
		frmChatAppServer.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Server Port");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1.setBackground(Color.DARK_GRAY);
		lblNewLabel_1_1.setBounds(218, 261, 57, 25);
		frmChatAppServer.getContentPane().add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setText("8080");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_1.setColumns(10);
		textField_1.setBounds(297, 294, 40, 20);
		frmChatAppServer.getContentPane().add(textField_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("WS Port");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1_1.setBackground(Color.DARK_GRAY);
		lblNewLabel_1_1_1.setBounds(274, 261, 63, 25);
		frmChatAppServer.getContentPane().add(lblNewLabel_1_1_1);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(46, 139, 87));
		separator.setBackground(new Color(255, 255, 255));
		separator.setBounds(285, 261, 2, 55);
		frmChatAppServer.getContentPane().add(separator);
		PrintStream con = new PrintStream(os, false);
		System.setOut(con);
		System.setErr(con);
		
		
		
	}
}
