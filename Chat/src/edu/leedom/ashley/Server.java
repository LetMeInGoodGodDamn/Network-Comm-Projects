package edu.leedom.ashley;
import java.io.IOException;
import java.net.*;
import java.security.SecureRandom;
import java.util.*;

public class Server implements Runnable {
	private List<ServerClient> clients = new ArrayList<ServerClient>();
	private int port;
	private DatagramSocket socket;
	private Thread run, send, receive;
	private boolean running = false;
	
	public Server( int port )
	{
		this.port = port;
		
		try {
			socket = new DatagramSocket( port );
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		
		run = new Thread( this, "Server" );
		run.start();
	}
	
	public void run () 
	{
		running = true;
		receive();
	}
	
	private void receive()
	{
		receive = new Thread( "Receive " ) {
			public void run() {
				while( running ) {
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
	}
	
	private void process( DatagramPacket packet )
	{
		String data = new String( packet.getData() );
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
		else if ( data.startsWith( "/m/" ) )
		{
			sendToAll(data);
			System.out.println( data );
		}
		else
		{
			System.out.println( data );
		}
	}
	
	//initially called. It appends the ending character to any given message
	//then it calls the actual send method that interacts with clients
	private void send( String message, InetAddress address, int port )
	{
		message += "/e/";
		send( message.getBytes(), address, port );
	}
	
	//send to all will send client messages to other clients
	private void sendToAll( String message ) 
	{
		for( int i = 0; i < clients.size(); i++ )
		{
			ServerClient client = clients.get(i);
			send( message.getBytes(), client.address, client.port );
		}
	}
	
	//wraps the message up in a packet and sends it out
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
	}
}
