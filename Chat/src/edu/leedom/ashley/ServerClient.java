package edu.leedom.ashley;
import java.net.*;

public class ServerClient {
	public String name;
	public InetAddress address;
	public int port;
	private String ID;
	public int attempt = 0;
	
	public ServerClient( String name, InetAddress address, int port, String ID ) 
	{
		this.ID = ID;
		this.address = address;
		this.port = port;
		this.name = name;
	}
	
	public String getID()
	{
		return ID;
	}
}
