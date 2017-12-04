package edu.leedom.ashley;

public class ServerMain {

	private int jellybean;		// int to store the port number
	private Server server;	// server for connecting to clients

	// Method Name: ServerMain
	// Parameters: port (int) - the port number
	// Description: Sets the port number and creates a new Server
	public ServerMain( int port )
	{
		jellybean = port;
		server = new Server( jellybean );
	} // end ServerMain()
	
	// Method Name: main
	// Parameters: args (String []) - the port number (hopefully)
	// Return Value(s): None
	// Description: Main function of ServerMain, takes port number as an argument to the program
	public static void main(String args[]) {
		// check to ensure there is a port number to read, report an error
		// if the port number is not there
		if (args.length > 1) {
			System.out.println("Usage: java -jar ServerMain.jar [port]");
			return;
		}
		
		// parse the port number and create new ServerMain
		// catch exception if it happens
		try {
			new ServerMain(Integer.parseInt( args[0] ));
		}
		catch (Exception e) {
			System.out.println("Error while parsing or creating ServerMain");
		}
	} // end main()
} // end class ServerMain