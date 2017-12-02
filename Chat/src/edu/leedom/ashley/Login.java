package edu.leedom.ashley;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {

	private JPanel contentPane;			// jpanel for the login window
	private JTextField txtName;			// text field for the username
	private JTextField txtIpaddress;	// text field for the ipaddress
	private JTextField txtPort;			// text field for the port number

	// Method Name: main
	// Parameters: None
	// Return Value(s): None
	// Description: Main function of the program starts everything
	public static void main(String[] args) {
		// start the login frame on a new thread
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					// makes the frame visible to the user
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} // end main()

	// Method Name: Login
	// Parameters: None
	// Description: Creates new login frame and creates a client window
	public Login() {
		// java gui stuff created through windowbuilder
		setResizable(false);
		setBackground(SystemColor.activeCaption);
		
		// emulates the window any os it is running in
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Chat Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 350);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBackground(Color.GREEN);
		txtName.setHorizontalAlignment(SwingConstants.LEFT);
		txtName.setBounds(57, 53, 180, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsername.setBounds(109, 33, 75, 14);
		contentPane.add(lblUsername);
		
		JLabel lblAddressIP = new JLabel("IP Address:");
		lblAddressIP.setForeground(Color.WHITE);
		lblAddressIP.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAddressIP.setBounds(109, 100, 75, 14);
		contentPane.add(lblAddressIP);
		
		txtIpaddress = new JTextField();
		txtIpaddress.setBackground(Color.GREEN);
		txtIpaddress.setBounds(57, 118, 180, 20);
		contentPane.add(txtIpaddress);
		txtIpaddress.setColumns(10);
		
		JLabel lblPrt = new JLabel("Port:");
		lblPrt.setForeground(Color.WHITE);
		lblPrt.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrt.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPrt.setBounds(109, 163, 75, 14);
		contentPane.add(lblPrt);
		
		txtPort = new JTextField();
		txtPort.setBackground(Color.GREEN);
		txtPort.setColumns(10);
		txtPort.setBounds(57, 181, 180, 20);
		contentPane.add(txtPort);
		
		
		// login button. actionListener
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//grabs info from the text fields and throws them into the login method
				//login method will log the new client in (duh)
				String name = txtName.getText();
				String address = txtIpaddress.getText();
				int port = Integer.parseInt( txtPort.getText() );
				login(name, address, port);
			}
		});
		
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setBackground(Color.GREEN);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLogin.setBounds(102, 256, 89, 23);
		contentPane.add(btnLogin);
		
		// allows the user to use the enter key
		txtPort.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login(txtName.getText(), txtIpaddress.getText(), Integer.parseInt(txtPort.getText()));
				}
			}
		});
	} // end Login()
	 
	// Method Name: login
	// Parameters: name (String) - the username of user
	//			   address (String) - the ipaddress of the user
	//			   port (int) - the port number of the server
	// Return Value(s): none
	// Description: Closes login window and creates a new ClientWindow
	private void login( String name, String address, int port )
	{
		// closes login window
		dispose();
		
		// create new ClientWindow
		new ClientWindow( name, address, port );
	} // end login()
} // end class Login