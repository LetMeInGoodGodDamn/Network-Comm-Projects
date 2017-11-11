import java.io.*;
import java.net.*;

public class ChatThread {

	private static String filename;
	private static Socket clientSocket;
	
	public ChatThread( Socket cs, String fn )
	{
		filename = fn;
		
		try {
			clientSocket = cs;
		}
		catch(Exception e)
		{
			System.out.println( "Error creating clientSocket threadside.");
			System.out.println( e.getMessage() );
			System.exit(1);
		}
	}
	
	public void run()
	{
		try (
				PrintWriter out = new PrintWriter( clientSocket.getOutputStream(), true );
				BufferedReader in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
			)
			{
				String inputLine, outputLine;
				
			}
			catch(IOException e )
			{
				System.out.println( "Exception caught when trying to listen on port number or listening for a connection" );
				System.out.println( e.getMessage() );
				System.exit(1);
			}	
	}
}
