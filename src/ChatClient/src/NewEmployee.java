import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewEmployee extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;


	public static void showPanel() {
		try {
			NewEmployee dialog = new NewEmployee();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Create the dialog.
	 */
	public NewEmployee() {
		setResizable(false);
		setTitle("Add Employee");
		setBounds(100, 100, 339, 277);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBounds(0, 0, 323, 195);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("ID");
			lblNewLabel.setForeground(Color.WHITE);
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblNewLabel.setBounds(10, 28, 69, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Name");
			lblNewLabel_1.setForeground(Color.WHITE);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblNewLabel_1.setBounds(10, 59, 69, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Surname");
			lblNewLabel_2.setForeground(Color.WHITE);
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblNewLabel_2.setBounds(10, 90, 69, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Email");
			lblNewLabel_3.setForeground(Color.WHITE);
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblNewLabel_3.setBounds(10, 121, 69, 14);
			contentPanel.add(lblNewLabel_3);
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Citizenship");
			lblNewLabel_4.setForeground(Color.WHITE);
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblNewLabel_4.setBounds(10, 152, 69, 14);
			contentPanel.add(lblNewLabel_4);
		}
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(79, 25, 231, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setBounds(79, 56, 231, 20);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setBounds(79, 87, 231, 20);
		contentPanel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setBounds(79, 118, 231, 20);
		contentPanel.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(79, 149, 231, 20);
		contentPanel.add(textField_4);
		textField_4.setHorizontalAlignment(SwingConstants.CENTER);
		textField_4.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setBounds(0, 192, 323, 46);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			
			JGradientButton grdntbtnCancel = new JGradientButton();
			grdntbtnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((JDialog)SwingUtilities.getWindowAncestor(contentPanel)).dispose();
				}
			});
			grdntbtnCancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
			grdntbtnCancel.setBackground(new Color(205, 92, 92));
			grdntbtnCancel.setText("Cancel");
			grdntbtnCancel.setBounds(10, 11, 102, 24);
			buttonPane.add(grdntbtnCancel);
			
			JGradientButton grdntbtnAddEmployee = new JGradientButton();
			grdntbtnAddEmployee.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String ip = NetworkManager.serverIP;
					
					String url = String.format("http://%s:8080/data/addemployee=%s,%s,%s,%s,%s", ip, textField.getText(), textField_1.getText(), textField_2.getText(), textField_3.getText(), textField_4.getText());
					
					url = url.replaceAll("ı", "i").replaceAll("ç", "c").replaceAll("ö", "o").replaceAll("ş", "s").replaceAll("ü", "u").replaceAll("ğ", "g");
					
					String res = WebServiceWindow.getHTML(url);
					
					WebServiceWindow.printJSON(res);
					
					((JDialog)SwingUtilities.getWindowAncestor(contentPanel)).dispose();
				}
			});
			grdntbtnAddEmployee.setFont(new Font("Tahoma", Font.PLAIN, 11));
			grdntbtnAddEmployee.setBackground(new Color(85, 107, 47));
			grdntbtnAddEmployee.setText("Add Employee");
			grdntbtnAddEmployee.setBounds(211, 11, 102, 24);
			buttonPane.add(grdntbtnAddEmployee);
		}
	}
}
