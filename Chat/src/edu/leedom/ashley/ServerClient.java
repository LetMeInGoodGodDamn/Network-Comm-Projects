package edu.leedom.ashley;

import java.net.*;

public class ServerClient {
	public String name;			// String to store the username
	public InetAddress address;	// InetAddress to store the ipaddess of a user
	public int port;			// int to store the port number
	private int ID;				// int to store the unique id of a user 
	
	// Method Name: ServerClient
	// Parameters: name (String) - the username of the client
	// 			   address (InetAddress) - the ipaddress of the user
	// 			   port (int) - the port number
	// 			   ID (int) - the unique id of the user
	// Description: Creates a new ServerClients and sets data field
	public ServerClient( String name, InetAddress address, int port, int ID ) 
	{
		this.ID = ID;
		this.address = address;
		this.port = port;
		this.name = name;
	} // end ServerClient()
	
	// Method Name: getID
	// Parameters: None
	// Return Value(s): int - the client's ID
	// Description: returns the the client's unique id
	public int getID()
	{
		return ID;
	} // end getID()
} // end class ServerClient
