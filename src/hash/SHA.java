package hash;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

public class SHA {

	public static String SHA_1 = "SHA-1";
	public static String SHA_224 = "SHA-224";
	public static String SHA_256 = "SHA-256";
	public static String SHA_384 = "SHA-384";
	public static String SHA_512_224 = "SHA-512/224";
	public static String SHA_512_256 = "SHA-512/256";
	
	public String hash(String input, String algorithsm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithsm);
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String hashFile(String file, String algorithsm) throws Exception {
		MessageDigest digest = MessageDigest.getInstance(algorithsm);
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		DigestInputStream dis = new DigestInputStream(is, digest);
		
		byte[] buffer = new byte[1024];
		int read;
		
		do {
			read = dis.read(buffer);
		}while(read != -1);
			BigInteger number = new BigInteger(1, dis.getMessageDigest().digest());
			
		return number.toString(16);
	}
}
