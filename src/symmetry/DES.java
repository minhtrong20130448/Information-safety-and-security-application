package symmetry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DES {
	private SecretKey key;

	public void setKey(SecretKey key) {
		this.key = key;
	}

	public SecretKey getKey() {
		return this.key;
	}

	public void createKey(int sizeKey) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		keyGenerator.init(sizeKey);
		key = keyGenerator.generateKey();
	}

	public void createKeyDES(int sizeKey) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		keyGenerator.init(sizeKey);
		key = keyGenerator.generateKey();
	}

	
	public void createKeyAvailable(String keyString) {
		try {
			byte[] keyBytes = keyString.getBytes("UTF-8");
			Cipher cipher = Cipher.getInstance("DES");
			key = new SecretKeySpec(keyBytes, "DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (Exception e) {
			key = null;
			throw new IllegalArgumentException("Wrong size key!");
		}
	}

	public byte[] encript(String text) throws Exception {
		if (key == null)
			return new byte[] {};
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] plainText = text.getBytes("UTF-8");
		byte[] cipherText = cipher.doFinal(plainText);
		return cipherText;
	}

	public String encriptToBase64(String text, SecretKey key) throws Exception {
		if (key == null)
			throw new IllegalArgumentException("Key is null");
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] plainText = text.getBytes("UTF-8");
		byte[] cipherText = cipher.doFinal(plainText);
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public String decrypt(byte[] text) throws Exception {
		if (key == null)
			return null;
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plainText = cipher.doFinal(text);
		return new String(plainText, "UTF-8");
	}

	public String decyptToBase64(String text, SecretKey key) throws Exception {
		if (key == null)
			throw new Error("Key is null");
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(text));
		String res = new String(plainText, "UTF-8");
		return res;
	}

	public void encryptFile(String sourceFile, String dest) throws Exception {
		if (key == null)
			throw new FileNotFoundException("Key not found");
		File file = new File(sourceFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(dest);
			byte[] input = new byte[64];

			int byteRead;
			while ((byteRead = fis.read(input)) != -1) {
				byte[] output = cipher.update(input, 0, byteRead);
				if (output != null) {
					fos.write(output);
				}
			}

			byte[] output = cipher.doFinal();

			if (output != null) {
				fos.write(output);
			}

			fis.close();
			fos.flush();
			fos.close();

			System.out.println("Encrypted");
		} else {
			System.out.println("This isn't not file");
		}
	}

	public void decryptFile(String sourceFile, String dest) throws Exception {
		if (key == null)
			throw new FileNotFoundException("Key not found");
		File file = new File(sourceFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(dest);
			byte[] input = new byte[64];

			int byteRead;
			while ((byteRead = fis.read(input)) != -1) {
				byte[] output = cipher.update(input, 0, byteRead);
				if (output != null) {
					fos.write(output);
				}
			}

			byte[] output = cipher.doFinal();

			if (output != null) {
				fos.write(output);
			}

			fis.close();
			fos.flush();
			fos.close();
		} else {
			System.out.println("This isn't not file");
		}
	}
	
	public String bytesToHex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes) {
			result.append(String.format("%02X", b));
		}
		return result.toString();
	}
}
