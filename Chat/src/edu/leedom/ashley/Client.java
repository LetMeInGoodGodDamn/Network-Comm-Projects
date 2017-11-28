package edu.leedom.ashley;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	private String name, address;
	private int port;
	private int id = -1;
	
	private DatagramSocket socket;
	private InetAddress ip;
	private String ID = null;
	private Thread send;

	public Client( String name, String address, int port ){
		this.name = name;
		this.address = address;
		this.port = port;
	}
	
	// BEGINNING OF NETWORKING SHITE
	// receive data packets yo
	public String receive() {
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);

		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String message = new String(packet.getData());
		
		return message;
	}

	public void send(final byte[] data) {
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

	public boolean openConnection(String address) {
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public String getName()
	{
		return name;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public void setID( int id )
	{
		this.id = id;
	}
}