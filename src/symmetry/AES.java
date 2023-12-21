package symmetry;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.util.Base64;

public class AES {
	private SecretKey key;

	public SecretKey getKey() {
		if (key == null)
			return null;
		return key;
	}

	public void setKey(SecretKey key) {
		this.key = key;
	}

	public AES() {
	}
	

    public void generateAESKey(int keySize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize);
        key = keyGen.generateKey();
    }
    
	public void entedKeyAvailable(String keyString) {
		try {
			byte[] keyBytes = keyString.getBytes("UTF-8");
			 Cipher cipher = Cipher.getInstance("AES");
			 key = new SecretKeySpec(keyBytes, "AES");
		     cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (Exception e) {
			key = null;
			throw new IllegalArgumentException("Wrong size key!");
		}
	}
	

    public String encryptAES(String plainText, Key secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }


    public String decryptAES(String encryptedText, Key secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

	public String bytesToHex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes) {
			result.append(String.format("%02X", b));
		}
		return result.toString();
	}
}