import java.util.*;
import java.io.*;
import java.net.*;

public class ChatServer {
	private static final int EXIT_FAILURE = 1;
	private static final int BUF_SIZE = 1000;
	private static Users[] userList = new Users[50];
	private static int userNum = 0;

	public static void main(String[] args) {
		// accepts 1 argument: port number
		if (args.length != 1) {
			System.err.printf("usage: java Server <port number>\n");
			System.exit(EXIT_FAILURE);
		}

		int portNumber = Integer.parseInt(args[0]);
		Login thing = new Login();
		try (DatagramSocket serverSocket = new DatagramSocket(portNumber);) {
			// creates a buffer of BUF_SIZE bytes
			do {
				byte[] buf = new byte[BUF_SIZE];
				// creates a packet based on buffer size
				DatagramPacket packet = new DatagramPacket(buf, buf.length);

				// while server is bound, receives and sends packets to the client
				// while (serverSocket.isBound()) {
				// receives packet from client
				serverSocket.receive(packet);
				// gets packet address and port info of client
				InetAddress address = packet.getAddress();
				String ipAdd = address.toString();
				int port = packet.getPort();
				String received = ipAdd + ": " + new String(packet.getData(), 0, packet.getLength());

				// stores packet data in buffer
				buf = received.getBytes();
				// packet = new DatagramPacket(buf, buf.length, address, port);
				// packet = new DatagramPacket(buf, buf.length, userList[i].ip, port);
				// serverSocket.send(packet);

				// repackage packet and send it back to client
				System.out.println(thing.getUserNum());
				for (int i = 0; i < thing.getUserNum(); i++) {
					packet = new DatagramPacket(buf, buf.length, thing.getUserList()[i].ip, port);
					System.out.println(thing.getUserList()[i].ip);
					serverSocket.send(packet);
				}

			} while (serverSocket.isBound());

		} catch (IOException error) {
			System.out.printf("Error when trying to listen on port %d or listening for connection\n", portNumber);
			System.out.println(error.getMessage());
		}
	}

	/*public static void addNewClient(String name, String ipAddress) throws UnknownHostException {
		// userlist shite
		boolean valid = true;
		InetAddress address = InetAddress.getByName(ipAddress);
		if (userNum == 0) {
			userList[userNum] = new Users(name, address);
			userNum++;
			System.out.println("if==0" + userNum);
		} else {
			// make sure to not double up on the clients
			for (int i = 0; i <= userNum; i++) {
				if (userList[i].username == name) {
					valid = false;
					System.out.println("else" + userNum);
					break;
				}
			}
		}
		if (valid) {
			userList[userNum] = new Users(name, address);
			userNum++;
			System.out.println("user++" + userNum);
		} // TODO Auto-generated method stub
	}*/
}
