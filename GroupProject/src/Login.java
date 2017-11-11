
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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtIpaddress;
	private JTextField txtPort;
	public static Users[] userList = new Users[50];
	public static int userNum = 0;

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
				try {
					login(name, address, port);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setBackground(SystemColor.activeCaption);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLogin.setBounds(102, 256, 89, 23);
		contentPane.add(btnLogin);
	}
	
	//logs the user in
	private void login( String name, String address, int port ) throws UnknownHostException
	{
		dispose();
		System.out.printf( "Name: %s\nAddress: %s\nPort: %d", name, address, port );
		addNewClient(name, address);
		ChatClient test = new ChatClient( name, address, port );
	}
	public Users[] getUserList() {
		return userList;
	}
	public int getUserNum() {
		return userNum;
	}
	public void addNewClient(String name, String ipAddress) throws UnknownHostException {
		// userlist shite
		boolean valid = true;
		InetAddress address = InetAddress.getByName(ipAddress);
		if (userNum == 0) {
			userList[userNum] = new Users(name, address);
			userNum++;
			System.out.println("if==0" + userNum);
		} else {
			// make sure to not double up on the clients
			for (int i = 0; i <= userNum; i++) {
				if (userList[i].username == name) {
					valid = false;
					System.out.println("else" + userNum);
					break;
				}
			}
		}
		if (valid) {
			userList[userNum] = new Users(name, address);
			userNum++;
			System.out.println("user++" + userNum);
		} // TODO Auto-generated method stub
	}

	
}