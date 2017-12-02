package edu.leedom.ashley;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
	// the list of connected clients
	private List<ServerClient> clients = new ArrayList<ServerClient>(); 
	private int port; // int to store the port number
	private DatagramSocket socket; // the socket
	// threads for running, sending, receiving
	private Thread run, send, receive;  
	private boolean running = false; // boolean variable 
	
	// Method Name: Server
	// Parameters: port (int) - the port number
	// Description: Creates a new DatagramSocket and runs the server on a seperate thread
	public Server( int port )
	{
		// sets the port number to the passed in
		this.port = port;
		
		// try to create a new datagramsocket with the given port number
		// catch an exception if it is thrown and return
		try {
			socket = new DatagramSocket( port );
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		
		// create a new thread to run the server and start it
		run = new Thread( this, "Server" );
		run.start();
	} // end Server()
	
	// Method Name: run
	// Parameters:	None
	// Return Value(s): None
	// Description: Sets running to be true and calls receive
	public void run () 
	{
		running = true;
		receive();
	} // end run()
	
	// Method Name: receive
	// Parameters: None
	// Return Value(s): None
	// Description: Receives a new packet from the socket
	private void receive()
	{
		receive = new Thread( "Receive " ) {
			public void run() {
				while( running ) {
					// byte array for the  packet
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket( data, data.length);
					try {
						socket.receive( packet );
					} catch (IOException e) {
						e.printStackTrace();
					}
					process( packet );
				}
			}
		};
		receive.start();
	} // end receive()
	
	// Method Name: process
	// Parameters: packet (DatagramPacket) - the packet to process
	// Return Value(s): None
	// Description: Processes the pocket and either creates a new unique id 
	//				and adds the new client to the client list or prints 
	//				the message
	private void process( DatagramPacket packet )
	{
		// create a new string from the data in the packet
		String data = new String( packet.getData() );
		
		// if the string starts with /c/, it is a connection packet
		// create a new unique id and add the new client to the client list
		if( data.startsWith( "/c/" ) )
		{
			int id = UniqueIdentifier.getIdentifier();
			clients.add( new ServerClient( data.substring( 3, data.length() ), 
									       packet.getAddress(), packet.getPort(), 
									       id ) );
			//verify connection
			String ID = "/c/" + id;
			send( ID, packet.getAddress(), packet.getPort() );
		}
		// else the packet is a message to be sent to all other clients
		else if ( data.startsWith( "/m/" ) )
		{
			sendToAll(data);
		}
	} // end process()

	// Method Name: send
	// Parameters: message (String) - the message to send
	// 			   address (InetAddress) - the address to send the message to
	// 			   port (int) - the port number 
	// Return Value(s): None
	// Description:	Initially called, it appends the ending character to any 
	//				given message then it calls the actual send method that 
	// 				interacts with clients
	private void send( String message, InetAddress address, int port )
	{
		// add the ending character to the message
		message += "/e/";
		// calls the send method that will actually send the message to the 
		// clients
		send( message.getBytes(), address, port );
	} // end send()
	
	// Method Name: sendToAll
	// Parameters: message (String) - the message to send
	// Return Value(s): None
	// Description: Send the messages to other clients
	private void sendToAll( String message ) 
	{
		for( int i = 0; i < clients.size(); i++ )
		{
			ServerClient client = clients.get(i);
			send( message.getBytes(), client.address, client.port );
		}
	} // end sendToAll()
	
	// Method Name: send
	// Parameters: data (final byte[]) - the data to send
	// 			   address (final InetAddress) - the addres to send to
	//			   port (int) - the port numner
	// Return Value(s): None
	// Description: Wraps the message up in a packet and sends it out
	private void send( final byte[] data, final InetAddress address, final int port )
	{
		send = new Thread( "Send" ) {
			public void run() {
				DatagramPacket packet = new DatagramPacket( data, data.length, address, port );
				try {
					socket.send( packet );
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		send.start();
	} // end send()
} // end class Server
