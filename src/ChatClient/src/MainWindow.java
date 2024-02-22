import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import javax.swing.border.MatteBorder;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class MainWindow implements ActionListener{

	public static JFrame frmChatApp;
	public static JList<String> list;
	public static JTextPane textPane;
	public static JGradientButton grdntbtnS;
	public static JTextArea textArea;
	public static JScrollPane jsp;
	public static JCheckBox chckbxNewCheckBox;
	NetworkManager nm = null;

	/**
	 * Create the application.
	 */
	public static Auth dialog;
	public MainWindow() {
		
		if(nm == null) {
			nm = new NetworkManager();
			NetworkManager.connect();
		}
			
		
		initialize();

		dialog = new Auth();
		
		dialog.setVisible(true);
		
		dialog.setLocationRelativeTo(null);
		
		
		
		frmChatApp.setVisible(false);
		
	}
	public static DefaultListModel<String> model = new DefaultListModel<>();
	
	
	public static String lastSelectedPerson = "Global";
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatApp = new JFrame();
		frmChatApp.getContentPane().setBackground(new Color(119, 136, 153));
		frmChatApp.setResizable(false);
		frmChatApp.setBackground(new Color(230, 230, 250));
		frmChatApp.setTitle("Chat App");
		frmChatApp.setBounds(100, 100, 550, 426);
		frmChatApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatApp.getContentPane().setLayout(null);
		frmChatApp.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBackground(new Color(128, 128, 128));
		panel.setBounds(10, 269, 382, 109);
		frmChatApp.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		

		textArea = new JTextArea();

		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setForeground(new Color(255, 255, 255));
		textArea.setBackground(new Color(47, 79, 79));
		panel.add(textArea, BorderLayout.CENTER);
		
		JGradientButton grdntbtnSend = new JGradientButton();
		panel.add(grdntbtnSend, BorderLayout.EAST);
		grdntbtnSend.addActionListener(new ActionListener() {
			   
			public void actionPerformed(ActionEvent e) {
				String s = textArea.getText();
				textArea.setText("");
				
				if(s.equals("")) return;
				
				
				String receiver = list.getSelectedValue();
				if(receiver == null) receiver = "Global";

				
				NetworkManager.sendMessage(receiver, s);
				
			}
		});
		grdntbtnSend.setBackground(new Color(119, 136, 153));
		grdntbtnSend.setText("Send");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setBackground(new Color(46, 139, 87));
		panel_1.setBounds(10, 11, 382, 244);
		frmChatApp.getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		textPane = new JTextPane();
	
		jsp = new JScrollPane(textPane);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textPane.setEditable(false);
		textPane.setForeground(new Color(255, 255, 255));
		textPane.setBackground(new Color(54, 57, 63));
		

		panel_1.add(jsp, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_2.setBackground(new Color(169, 169, 169));
		panel_2.setBounds(402, 269, 122, 109);
		frmChatApp.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JGradientButton grdntbtnQuit = new JGradientButton();
		grdntbtnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		grdntbtnQuit.setBackground(new Color(250, 128, 114));
		grdntbtnQuit.setText("Quit");
		grdntbtnQuit.setBounds(10, 68, 102, 30);
		panel_2.add(grdntbtnQuit);
		
		grdntbtnS = new JGradientButton();
		grdntbtnS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new WebServiceWindow();
				WebServiceWindow.ShowWebServiceWindow();
			}
		});
		grdntbtnS.setBounds(10, 11, 102, 23);
		panel_2.add(grdntbtnS);
		grdntbtnS.setFont(new Font("Tahoma", Font.PLAIN, 11));
		grdntbtnS.setText("Backdoor");
		grdntbtnS.setBackground(new Color(100, 149, 237));
		grdntbtnS.setVisible(false);
		
		chckbxNewCheckBox = new JCheckBox("Enable Sounds");
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxNewCheckBox.setBackground(new Color(169, 169, 169));
		chckbxNewCheckBox.setBounds(10, 41, 102, 23);
		panel_2.add(chckbxNewCheckBox);
		
		JLabel lblNewLabel_1 = new JLabel("ChatApp v1.0");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 15, 102, 14);
		panel_2.add(lblNewLabel_1);
		
		
		textArea.addKeyListener(new KeyListener(){
		    @Override
		    public void keyPressed(KeyEvent e){
		        if(e.getKeyCode() == KeyEvent.VK_ENTER){
		        	e.consume();
		        	grdntbtnSend.doClick();
		        }
		    }

		    @Override
		    public void keyTyped(KeyEvent e) {
		    }

		    @Override
		    public void keyReleased(KeyEvent e) {
		    }
		});
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_3.setBackground(new Color(0, 0, 0));
		panel_3.setBounds(402, 11, 122, 244);
		frmChatApp.getContentPane().add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		
		list = new JList<>(model);
		list.setBackground(new Color(143, 188, 143));
		list.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		
		list.setSelectedIndex(0);
		
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				
				if (!list.isSelectionEmpty()) 
					lastSelectedPerson = list.getSelectedValue();
			}
		});
		
		
		panel_3.add(list, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("         Online Users");
		lblNewLabel.setForeground(new Color(50, 205, 50));
		panel_3.add(lblNewLabel, BorderLayout.NORTH);
		
		URL resource = getClass().getResource("/Icon.png");
		ImageIcon tmp = new ImageIcon(resource);
		frmChatApp.setIconImage(tmp.getImage());
		
		TextPaneOutputStream os= new TextPaneOutputStream(textPane, "");
		PrintStream con = new PrintStream(os, false);
		System.setOut(con);
		System.setErr(con);
		
		Timer timer = new Timer(200, this);
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!MainWindow.dialog.isDisplayable())
			System.exit(0);
	}
}
