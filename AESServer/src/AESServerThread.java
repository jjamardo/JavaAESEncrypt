import java.io.DataInputStream;
import org.apache.commons.codec.binary.Base64;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

class AESServerThread extends Thread {
	DataInputStream in;
	DataOutputStream out;
	private Socket incoming;

	public AESServerThread(Socket i) {
		incoming = i;
	}

	public void run() {
		try {
			in = new DataInputStream(incoming.getInputStream());
			out = new DataOutputStream(incoming.getOutputStream());

			out.writeUTF("Connected!\n");
			out.flush();

			// Msg encrypted from client
			String str = in.readUTF();
			System.out.println("Received encrypted:" + str);

			if (str != null) {
				String secretKey = "XMzDdG4D03CKm2IxIWQw7g==";
				String decrypted = symmetricDecrypt(str, secretKey);
				System.out.println("Decrypted msg: " + decrypted);
			}

			incoming.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static String symmetricDecrypt(String text, String secretKey) {
		Cipher cipher;
		String encryptedString;
		byte[] encryptText = null;
		byte[] raw;
		SecretKeySpec skeySpec;
		try {
			raw = Base64.decodeBase64(secretKey);
			skeySpec = new SecretKeySpec(raw, "AES");
			encryptText = Base64.decodeBase64(text);
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			encryptedString = new String(cipher.doFinal(encryptText));
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		return encryptedString;
	}
}