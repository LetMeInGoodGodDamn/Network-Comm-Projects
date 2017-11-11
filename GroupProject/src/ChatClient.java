import java.util.*;
import java.io.*;
import java.net.*;

public class ChatClient {
	private static final int EXIT_FAILURE = 1;
	private static final int BUF_SIZE = 1000;
	private static String username;
	private static String ipAddress;
	private static int portnumber;

	public ChatClient(String name, String address, int port) {
		username = name;
		ipAddress = address;
		portnumber = port;
		runClient();
	}

	public static void runClient() {
		// takes 2 command line arguments
		// host name & port number
		try (
				// given port number is not used to create a DatagramSocket
				DatagramSocket echoSocket = new DatagramSocket();
				BufferedReader console = new BufferedReader(new InputStreamReader(System.in));) {
			// create a buffer that'll accept up to BUF_SIZE bytes
			byte[] buf = new byte[BUF_SIZE];
			InetAddress address = InetAddress.getByName(ipAddress);
			String userInput;
			System.out.println("Successfully connected client");

			// while user is inputting text, sends and receives packets from server
			while ((userInput = console.readLine()) != null) {
				// convert userInput to bytes and stores it in buf
				buf = userInput.getBytes();
				// creates a packet
				DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portnumber);
				// sends packet to server
				echoSocket.send(packet);
				byte[] buffpt2 = new byte[1000];
				// prepares to receive a packet from server
				packet = new DatagramPacket(buffpt2, buffpt2.length);
				// receives packet from server
				echoSocket.receive(packet);
				// unpacks information from packet and throws it into a string builder
				String received = new String(packet.getData(), 0, packet.getLength());
				// outputs the data to the console
				System.out.println( received);
			}

			// closes the socket because Eclipse was screaming at me
			echoSocket.close();
		} catch (UnknownHostException error) {
			System.err.printf("unknown host: %s\n", username);
			System.exit(EXIT_FAILURE);
		} catch (IOException error) {
			System.err.printf("unable to   establish I/O connection to %s\n", username);
			System.exit(EXIT_FAILURE);
		}
	}
}
