package symmetry;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class TripleDES {
	private SecretKey key;

	public SecretKey getKey() {
		if (key == null)
			return null;
		return key;
	}

	public void setKey(SecretKey key) {
		this.key = key;
	}

	public TripleDES() {
	}
	
	public void entedKeyAvailable(String keyString) {
		try {
			byte[] keyBytes = keyString.getBytes("UTF-8");
			Cipher cipher = Cipher.getInstance("DESede");
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
			key = factory.generateSecret(new DESedeKeySpec(keyBytes));
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (Exception e) {
			key = null;
			throw new IllegalArgumentException("Wrong size key!");
		}
	}
	
	public String encrypt(String plaintext, SecretKey secretKey) throws Exception {
		byte[] encryptedBytes;
		Cipher cipher = Cipher.getInstance("DESede");

		SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
		SecretKey key = factory.generateSecret(new DESedeKeySpec(secretKey.getEncoded()));

		cipher.init(Cipher.ENCRYPT_MODE, key);
		encryptedBytes = cipher.doFinal(plaintext.getBytes());

		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public String decrypt(String ciphertext, SecretKey secretKey) throws Exception {
		byte[] decryptedBytes;
		Cipher cipher = Cipher.getInstance("DESede");

		SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
		SecretKey key = factory.generateSecret(new DESedeKeySpec(secretKey.getEncoded()));

		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] ciphertextBytes = Base64.getDecoder().decode(ciphertext);
		decryptedBytes = cipher.doFinal(ciphertextBytes);

		return new String(decryptedBytes);
	}

	public void generateRandomKey(int sizeKey) throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
		keyGen.init(sizeKey);
		key = keyGen.generateKey();
	}

	public String bytesToHex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes) {
			result.append(String.format("%02X", b));
		}
		return result.toString();
	}
}
