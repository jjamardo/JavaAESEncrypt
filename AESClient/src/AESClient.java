import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import org.apache.commons.codec.binary.Base64;

class AESClient {
	public static void main(String[] args) {
		DataOutputStream out;
		DataInputStream in;

		try {
			Socket t = new Socket("127.0.0.1", 9003);
			in = new DataInputStream(t.getInputStream());
			out = new DataOutputStream(t.getOutputStream());

			// "Connected!" (from server)
			System.out.println(in.readUTF());

			String secretKey = "XMzDdG4D03CKm2IxIWQw7g==";
			String msg = "Singapore Malaysia Japan India Indonesia HongKong Taiwan China England";
			String encrypted = symmetricEncrypt(msg, secretKey);
			System.out.println("Sending encrypted: " + encrypted);
			out.writeUTF(encrypted);
			out.flush();

			t.close();

		} catch (IOException e) {
			System.out.println("Error");
		}
	}

	public static String symmetricEncrypt(String text, String secretKey) {
		byte[] raw;
		String encryptedString;
		SecretKeySpec skeySpec;
		byte[] encryptText = text.getBytes();
		Cipher cipher;
		try {
			raw = Base64.decodeBase64(secretKey);
			skeySpec = new SecretKeySpec(raw, "AES");
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			encryptedString = Base64.encodeBase64String(cipher.doFinal(encryptText));
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		return encryptedString;
	}
}