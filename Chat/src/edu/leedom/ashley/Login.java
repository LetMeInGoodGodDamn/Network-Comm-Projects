package edu.leedom.ashley;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtIpaddress;
	private JTextField txtPort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setResizable(false);
		setBackground(SystemColor.activeCaption);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("Chat Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setHorizontalAlignment(SwingConstants.LEFT);
		txtName.setBounds(57, 53, 180, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsername.setBounds(109, 33, 75, 14);
		contentPane.add(lblUsername);
		
		JLabel lblAddressIP = new JLabel("IP Address:");
		lblAddressIP.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAddressIP.setBounds(109, 100, 75, 14);
		contentPane.add(lblAddressIP);
		
		txtIpaddress = new JTextField();
		txtIpaddress.setBounds(57, 118, 180, 20);
		contentPane.add(txtIpaddress);
		txtIpaddress.setColumns(10);
		
		JLabel lblPrt = new JLabel("Port:");
		lblPrt.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrt.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPrt.setBounds(109, 163, 75, 14);
		contentPane.add(lblPrt);
		
		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(57, 181, 180, 20);
		contentPane.add(txtPort);
		
		
		//login button. actionListener
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
		btnLogin.setBackground(SystemColor.activeCaption);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLogin.setBounds(102, 256, 89, 23);
		contentPane.add(btnLogin);
	}
	
	//logs the user in
	private void login( String name, String address, int port )
	{
		dispose();
		new ClientWindow( name, address, port );
	}
}