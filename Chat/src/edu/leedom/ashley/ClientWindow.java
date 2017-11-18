package edu.leedom.ashley;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ClientWindow extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private String name, address;
	private int port;
	private JTextField txtMessage;
	private JTextArea txtrHistory;

	private DatagramSocket socket;
	private InetAddress ip;
	private String ID = null;
	private Thread send, run, listen;
	private boolean running = false;

	/**
	 * Create the frame.
	 */
	public ClientWindow(String name, String address, int port) {
		this.name = name;
		this.address = address;
		this.port = port;
		boolean connect = openConnection(address);

		if (!connect) {
			System.err.println("Connection failed!");
			console("Connection failed!");
			return;
		}

		createWindow();
		running = true;
		run = new Thread( "Running Thread" );
		run.start();
		String connection = "/c/" + name;
		send(connection.getBytes());
	}

	// more GUI shite
	private void createWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		setTitle("Client Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(880, 550);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 28, 815, 30, 7 };
		gbl_contentPane.rowHeights = new int[] { 35, 475, 40 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0 };
		gbl_contentPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		txtrHistory = new JTextArea();
		txtrHistory.setEditable(false);
		JScrollPane scroll = new JScrollPane(txtrHistory);
		GridBagConstraints scrollConstraints = new GridBagConstraints();
		scrollConstraints.insets = new Insets(0, 0, 5, 5);
		scrollConstraints.fill = GridBagConstraints.BOTH;
		scrollConstraints.gridx = 0;
		scrollConstraints.gridy = 0;
		scrollConstraints.gridwidth = 3;
		scrollConstraints.gridheight = 2;
		scrollConstraints.insets = new Insets(0, 5, 0, 0);
		contentPane.add(scroll, scrollConstraints);

		// listen for Enter key on txtMessage
		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					send(txtMessage.getText());
				}
			}
		});

		// sets up gridBagContraints for txtMessage
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMessage.gridx = 0;
		gbc_txtMessage.gridy = 2;
		gbc_txtMessage.gridwidth = 2;
		contentPane.add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);

		// le send button shite
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send(txtMessage.getText());
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 2;
		gbc_btnSend.gridy = 2;
		contentPane.add(btnSend, gbc_btnSend);

		setVisible(true);
		txtMessage.requestFocusInWindow();
	}

	// BEGINNING OF NETWORKING SHITE
	private void send(String message) {
		if (message.equals(""))
			return;

		console(name + ": " + message);
		message = "/m/" + message;
		send(message.getBytes());
		txtMessage.setText("");
	}

	public void console(String message) {
		txtrHistory.append(message + "\n\r");
		txtrHistory.setCaretPosition(txtrHistory.getDocument().getLength());
	}

	// receive data packets yo
	private String receive() {
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);

		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String message = new String(packet.getData());	
		console(message);
		return message;
	}

	public void listen() {
		listen = new Thread( "Listen" ) {
			public void run() {
				while (running) {
					console("in listen going to receive");
					String message = receive();

					if (message.startsWith("/c/")) {
						String id = message.split( "/c/|/e/")[1];
						ID = id;
						console( "Successfully connected to server." );
					}
					else if( message.startsWith( "/m/" ) ) 
					{
						//haven't done this shite yet
						console( message.substring( 3, message.length() ));
					}
				}
			}
		};
		listen.start();
	}

	public void run()
	{
		console("fuck you.");
		listen();
	}
	
	private void send(final byte[] data) {
		send = new Thread("Send") {
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		send.start();
	}

	private boolean openConnection(String address) {
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
