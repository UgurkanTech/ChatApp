import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Auth extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	JLabel lblNewLabel_3;


	/**
	 * Create the dialog.
	 */
	public Auth() {

		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
		setTitle("Chat Authentication");
		setBounds(100, 100, 399, 242);
		getContentPane().setLayout(null);
		
		URL resource = getClass().getResource("/Icon.png");
		ImageIcon tmp = new ImageIcon(resource);
		setIconImage(tmp.getImage());
		
		Image image = tmp.getImage();
		Image newimg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_FAST);  
		tmp = new ImageIcon(newimg);

		JLabel lblNewLabel = new JLabel("Chat App");
		lblNewLabel.setForeground(new Color(255, 250, 250));
		lblNewLabel.setFont(new Font("Ink Free", Font.BOLD, 34));
		lblNewLabel.setBounds(22, 11, 251, 71);
		lblNewLabel.setIcon(tmp);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setForeground(new Color(175, 238, 238));
		lblNewLabel_1.setFont(new Font("Century Schoolbook", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(32, 103, 67, 20);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setForeground(new Color(255, 99, 71));
		lblNewLabel_2.setFont(new Font("Century Schoolbook", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(31, 133, 87, 20);
		getContentPane().add(lblNewLabel_2);
		
		JGradientButton grdntbtnRegister = new JGradientButton();
		grdntbtnRegister.setBackground(new Color(144, 238, 144));
		grdntbtnRegister.setFont(new Font("Century Schoolbook", Font.PLAIN, 13));
		grdntbtnRegister.setText("Register");

		grdntbtnRegister.setBounds(142, 164, 102, 28);
		getContentPane().add(grdntbtnRegister);
		
		JGradientButton grdntbtnLogin = new JGradientButton();

		grdntbtnLogin.setBackground(new Color(70, 130, 180));
		grdntbtnLogin.setText("Login");
		grdntbtnLogin.setFont(new Font("Century Schoolbook", Font.PLAIN, 13));
		grdntbtnLogin.setBounds(271, 164, 102, 28);
		getContentPane().add(grdntbtnLogin);
		
		textField = new JTextField();
		textField.setBounds(142, 102, 231, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(142, 133, 231, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		lblNewLabel_3 = new JLabel("Connection:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(142, 77, 231, 14);
		getContentPane().add(lblNewLabel_3);
		
		grdntbtnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!ClientTCP.connected) {
					JOptionPane.showMessageDialog(null, "Connection problem!");
					return;
				}
				if (!textField.getText().equals("") && !textField_1.getText().equals("")) {
					NetworkManager.loginRequest(textField.getText(), textField_1.getText());
				}

			}
		});
		
		grdntbtnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!ClientTCP.connected) {
					JOptionPane.showMessageDialog(null, "Connection problem!");
					return;
				}
				if (!textField.getText().equals("") && !textField_1.getText().equals("")) {
					NetworkManager.registerRequest(textField.getText(), textField_1.getText());
				}
			}
		});
		
		getRootPane().setDefaultButton(grdntbtnLogin);
		Timer timer = new Timer(100, this);
		timer.start();
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		lblNewLabel_3.setText(ClientTCP.connected ? "Connected!" : "NOT Connected!" + "");
		lblNewLabel_3.setForeground(ClientTCP.connected ? Color.green : Color.red);
	}
}
