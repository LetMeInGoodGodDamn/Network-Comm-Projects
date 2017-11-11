import java.net.InetAddress;

public class Users {
	
	public String username;
	public boolean loggedIn;
	public InetAddress ip;
	
	public Users( String un, InetAddress add)
	{
		username = un;
		loggedIn = true;
		ip = add;
	}
	public void setIpAddress(InetAddress fuck) {
		ip = fuck;
	}
	public void logout()
	{
		loggedIn = false;
	}
	
	
	
}
