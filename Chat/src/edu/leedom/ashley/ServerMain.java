package edu.leedom.ashley;

public class ServerMain {

	private int port;
	private Server server;
	
	public ServerMain( int port )
	{
		this.port = port;
		server = new Server( this.port );
	
		//run -port
	}
	public static void main(String args[]) {
		int port;
		if (args.length > 1) {
			System.out.println("Usage: java -jar ServerMain.jar [port]");
			return;
		}

		port = Integer.parseInt( args[0] );
		new ServerMain(port);
	}
}
