package asymmetric;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class RSA {
	private KeyPair keyPair;
	private PublicKey publicKey;
	private PrivateKey privateKey;

	public void genKey() {
		KeyPairGenerator keyGenerator = null;
		try {
			keyGenerator = KeyPairGenerator.getInstance("RSA");
			keyGenerator.initialize(2048);
			keyPair = keyGenerator.generateKeyPair();
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] encrypt(String text, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] plaintext = text.getBytes(StandardCharsets.UTF_8);
		return cipher.doFinal(plaintext);
	}

	public String decrypt(byte[] text, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plaintext = cipher.doFinal(text);
		return new String(plaintext, StandardCharsets.UTF_8);
	}

	public String encryptRSAtoBase64(String text, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] plainText = text.getBytes("UTF-8");
		byte[] cipherText = cipher.doFinal(plainText);
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public String decryptRSAtoBase64(String text, Key privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] plainText = Base64.getDecoder().decode(text);
		byte[] cipherText = cipher.doFinal(plainText);
		return new String(cipherText, "UTF-8");
	}

	public void encryptFile(PublicKey publicKey, String source, String dest) throws Exception {
		File file = new File(source);
		if (file.exists()) {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest)));

			byte[] input = new byte[245];
			int bytesRead;

			while ((bytesRead = dis.read(input)) != -1) {
				byte[] encryptedBytes = cipher.doFinal(input, 0, bytesRead);
				dos.write(encryptedBytes);
			}

			dis.close();
			dos.flush();
			dos.close();

		}
	}

	public void decryptFile(PrivateKey privateKey, String source, String dest) throws Exception {
		File file = new File(source);
		if (file.exists()) {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
					DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest)))) {

				byte[] inputBytes = new byte[245]; // Độ dài của mỗi khối
				int bytesRead;
				while ((bytesRead = dis.read(inputBytes)) > 0) {
					byte[] decryptedBytes = cipher.doFinal(inputBytes, 0, bytesRead);
					dos.write(decryptedBytes);
				}

				dis.close();
				dos.flush();
				dos.close();
			}
		}
	}

	public void processFile(Cipher ci, InputStream in, OutputStream out)
			throws IOException, IllegalBlockSizeException, BadPaddingException {
		byte[] ibuf = new byte[1024];
		int len;
		while ((len = in.read(ibuf)) != -1) {
			byte[] obuf = ci.update(ibuf, 0, len);
			if (obuf != null)
				out.write(obuf);
		}
		byte[] obuf = ci.doFinal();
		if (obuf != null)
			out.write(obuf);

		in.close();
		out.flush();
		out.close();
	}

	public void doEncyptRSAWithAES(PrivateKey prikey, String inputFile, String outputFile) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();

		byte[] iv = new byte[128 / 8];
		SecureRandom srandom = new SecureRandom();
		srandom.nextBytes(iv);
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		try (FileOutputStream out = new FileOutputStream(outputFile)) {
			{
				Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				cipher.init(Cipher.ENCRYPT_MODE, prikey);
				byte[] b = cipher.doFinal(secretKey.getEncoded());
				out.write(b);
				System.out.println("AES Key length: " + b.length);
			}

			out.write(iv);
			System.out.println("IV length: " + iv.length);

			Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ci.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			try (FileInputStream in = new FileInputStream(inputFile)) {
				processFile(ci, in, out);
			}
		}
	}

	public void doDecryptRSAWithAES(PublicKey publicKey, String inputFile, String outputFile) throws Exception {
		try (FileInputStream in = new FileInputStream(inputFile)) {
			Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			rsaCipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] encryptedAesKey = new byte[256];
			in.read(encryptedAesKey);
			byte[] aesKeyBytes = rsaCipher.doFinal(encryptedAesKey);

			byte[] iv = new byte[128 / 8];
			in.read(iv);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);

			SecretKey aesKey = new SecretKeySpec(aesKeyBytes, 0, aesKeyBytes.length, "AES");
			Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);

			try (FileOutputStream out = new FileOutputStream(outputFile)) {
				processFile(aesCipher, in, out);
			}
		}
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}

	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

}
