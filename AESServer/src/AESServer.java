import java.net.*;

public class AESServer {
	public static void main(String[] args) {
		try {
			ServerSocket s = new ServerSocket(9003);
			Socket incoming = s.accept();
			System.out.println("Accepted!");
			new AESServerThread(incoming).start();
			s.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}