package edu.leedom.ashley;
import java.io.IOException;
import java.net.*;
import java.security.SecureRandom;
import java.util.*;

public class Server implements Runnable {
	private List<ServerClient> clients = new ArrayList<ServerClient>();
	private int port;
	private DatagramSocket socket;
	private Thread run, manage, send, receive;
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
		manageClients();
		receive();
	}
	
	private void manageClients() 
	{
		manage = new Thread( "Manage" ) {
			public void run()
			{
				while( running )
				{
					//Managing
					
				}
			}
		};
		manage.start();
		
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
			System.out.println( "We are in Server side /c/ " );
			UUID id = UUID.randomUUID();
			clients.add( new ServerClient( data.substring( 3, data.length() ), 
									       packet.getAddress(), packet.getPort(), 
									       id.toString() ) );
			//verify connection
			String id2 = "/c/" + id;
			send( id2, packet.getAddress(), packet.getPort() );
		}
		else if ( data.startsWith( "/m/" ) )
		{
			sendToAll(data);
			System.out.println( data );
		}
	}
	
	private void send( String message, InetAddress address, int port )
	{
		message += "/e/";
		send( message.getBytes(), address, port );
	}
	
	private void sendToAll( String message ) 
	{
		System.out.println( clients.size() );

		for( int i = 0; i < clients.size(); i++ )
		{
			ServerClient client = clients.get(i);
			send( message.getBytes(), client.address, client.port );
		}
	}
	
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
