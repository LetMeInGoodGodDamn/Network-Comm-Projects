package edu.leedom.ashley;

import java.io.IOException;
import java.net.*;

public class Client {
	private String name, address; // Strings to store the name and addresses
	private int port; // int to store the port number
	private int id = -1; // int to store the id
	
	private DatagramSocket socket; // datagram socket for messaging
	private InetAddress ip; // ip address of the user
	private String ID = null; // id of the user
	private Thread send; // thread for sending messages

	// Method Name: Client
	// Parameters: name (String) - the username
	//			   address (String) - the ip address
	// 			   port (int) - the port number
	// Return Value(s): None
	// Description: Creates a new client and sets data fields
	public Client( String name, String address, int port ){
		this.name = name;
		this.address = address;
		this.port = port;
	} // end Client()
	
	// Method Name: reveive
	// Parameters: None
	// Return Value(s): String - the message received
	// Description: Receives datagrampackets and return the message as a String
	public String receive() {
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);

		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// create a new string from the packet
		String message = new String(packet.getData());
		
		return message;
	} // end receive()

	// Method Name: send
	// Parameters: data (final byte []) - the data to send
	// Return Value(s): None
	// Description: Creates a new thread to send messages as datagrampackets
	public void send(final byte[] data) {
		send = new Thread("Send") {
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length, 
						ip, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		send.start();
	} // end send()

	// Method Name: openConnection
	// Parameters: address (String) - the ipaddress
	// Return Value(s): boolean - indicator of a successful connection
	// Description: Attempts to open a datagramsocket, return false it failed
	// 				or true if successful
	public boolean openConnection(String address) {
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	} // end openConnection()

	// Method Name: getName
	// Parameters: None
	// Return Value(s): String - the client's name
	// Description: Returns the client's name
	public String getName()
	{
		return name;
	} // end getName()
	
	// Method Name: getPort
	// Parameters: None
	// Return Value(s): int - the port number
	// Description: Returns the port number
	public int getPort()
	{
		return port;
	} // end getPort()
	
	// Method Name: setID
	// Parameters: id (int) - the id to be set
	// Return Value(s): None
	// Description: Sets the user's id to the int passed
	public void setID( int id )
	{
		this.id = id;
	} // end setID()
} // end class Client