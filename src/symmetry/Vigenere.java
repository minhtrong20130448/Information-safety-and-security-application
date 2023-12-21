package symmetry;

import java.security.SecureRandom;
import java.util.Random;

public class Vigenere {

	private final String ALPHABET = "AÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬBCDĐEÉÈẺẼẸÊẾỀỂỄỆFGHIÍÌỈĨỊJKLMNOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢPQRSTUÚÙỦŨỤƯỨỪỬỮỰVWXYÝỲỶỸỴZaáàảãạăắằẳẵặâấầẩẫậbcdđeéèẻẽẹêếềểễệfghiíìỉĩịjklmnoóòỏõọôốồổỗộơớờởỡợpqrstuúùủũụưứừửữựvwxyýỳỷỹỵz,.";
	
	private String key;
	
	public String getKey() {
		if (key == null || key.equals(""))
			return null;
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Vigenere() {
	}

	public String encryptVigenere(String text, String key) {
		StringBuilder encryptedText = new StringBuilder();
        int keyLength = key.length();
        
        for (int i = 0; i < text.length(); i++) {
            char plainChar = text.charAt(i);
            char keyChar = key.charAt(i % keyLength);
            
            int plainIdx = ALPHABET.indexOf(plainChar);
            int keyIdx = ALPHABET.indexOf(keyChar);
            
            if (plainIdx != -1) {
                int encryptedIdx = (plainIdx + keyIdx) % ALPHABET.length();
                encryptedText.append(ALPHABET.charAt(encryptedIdx));
            } else {
                encryptedText.append(plainChar);
            }
        }
		return encryptedText.toString();
	}

	public String decryptVigenere(String text, String key) {
		  StringBuilder decryptedText = new StringBuilder();
	        int keyLength = key.length();
	        
	        for (int i = 0; i < text.length(); i++) {
	            char cipherChar = text.charAt(i);
	            char keyChar = key.charAt(i % keyLength);
	            
	            int cipherIdx = ALPHABET.indexOf(cipherChar);
	            int keyIdx = ALPHABET.indexOf(keyChar);
	            
	            if (cipherIdx != -1) {
	                int decryptedIdx = (cipherIdx - keyIdx + ALPHABET.length()) % ALPHABET.length();
	                decryptedText.append(ALPHABET.charAt(decryptedIdx));
	            } else {
	                decryptedText.append(cipherChar);
	            }
	        }
	        return decryptedText.toString();
	}


	public void generateRandomKey() {
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder keyBuilder = new StringBuilder();
		Random ran = new Random();
		int length = ran.nextInt(11) + 10;
		for (int i = 0; i < length; i++) {
			int randomIndex = secureRandom.nextInt(ALPHABET.length());
			char randomChar = ALPHABET.charAt(randomIndex);
			keyBuilder.append(randomChar);
		}
		this.key = keyBuilder.toString();
	}

	public static void main(String[] args) {

		
	}
}
