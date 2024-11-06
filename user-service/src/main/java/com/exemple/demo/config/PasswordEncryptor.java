package com.exemple.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

@Component
public class PasswordEncryptor {

	private static final String ALGORITHM = "AES";
	private static final String ENCODING = "UTF-8";

	@Value("${encrypt.secretKey}")
	private String secretKey;

	public String encrypt(String input) {
		try {
			SecretKey key = new SecretKeySpec(secretKey.getBytes(ENCODING), ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedBytes = cipher.doFinal(input.getBytes(ENCODING));
			return URLEncoder.encode(Base64.getUrlEncoder().encodeToString(encryptedBytes), ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String decrypt(String encryptedInput) {
		try {
			SecretKey key = new SecretKeySpec(secretKey.getBytes(ENCODING), ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);

			String decodedInput = URLDecoder.decode(encryptedInput, ENCODING);
			byte[] decodedBytes = Base64.getUrlDecoder().decode(decodedInput);
			byte[] decryptedBytes = cipher.doFinal(decodedBytes);
			return new String(decryptedBytes, ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
