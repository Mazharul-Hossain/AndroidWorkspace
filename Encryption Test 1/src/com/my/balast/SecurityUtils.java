package com.my.balast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;

class SecurityUtils {

	private final static String cypher_algorithm = "AES/ECB/NoPadding";

	private static final byte[] salt = { (byte) 0x42, (byte) 0x20, (byte) 0x09,
			(byte) 0x99, (byte) 0x97, (byte) 0x77, (byte) 0x75, (byte) 0x55,
			(byte) 0x54, (byte) 0x44, (byte) 0x4B, (byte) 0xBB, (byte) 0xCC,
			(byte) 0xCC, (byte) 0xAF, (byte) 0xFF };

	private static int BLOCKS = 128;

	public static Boolean encryptAES(String seed, File inFile, File outFile)
			throws Exception {

		Log.d(" encryptAES ", "Encryption Starts.........");

		// byte[] rawKey = getRawKey(seed.getBytes("UTF8"));
		// byte[] rawKey = MD5(seed);
		byte[] rawKey = seed.getBytes("UTF8");
		SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");

		Cipher enCipher = Cipher.getInstance(cypher_algorithm);
		IvParameterSpec ivSpec = new IvParameterSpec(salt);
		enCipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);

		FileInputStream inStream = new FileInputStream(inFile);

		FileOutputStream outStream = new FileOutputStream(outFile);

		int fileSize = (int) inFile.length();

		byte[] plaintext = new byte[fileSize];

		inStream.read(plaintext);
		byte[] ciphertext = enCipher.doFinal(plaintext);
		outStream.write(ciphertext);

		inStream.close();
		outStream.close();

		Log.d(" encryptAES ", "Encryption Complete.........");

		return true;
	}

	public static Boolean decryptAES(String seed, File inFile, File outFile)
			throws Exception {

		Log.d(" decryptAES ", "Decryption Starts.........");

		// byte[] rawKey = MD5(seed);
		byte[] rawKey = seed.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");

		Cipher deCipher = Cipher.getInstance(cypher_algorithm);
		// IvParameterSpec ivSpec = new IvParameterSpec(salt);
		deCipher.init(Cipher.DECRYPT_MODE, skeySpec);

		FileInputStream inStream = new FileInputStream(inFile);

		FileOutputStream outStream = new FileOutputStream(outFile);

		int fileSize = (int) inFile.length();

		byte[] cipertext = new byte[fileSize];

		inStream.read(cipertext);

		byte[] recovertext = deCipher.doFinal(Base64.decode(cipertext));
		outStream.write(recovertext);

		inStream.close();
		outStream.close();

		Log.d(" decryptAES ", "Decryption Complete.........");

		return true;

	}

	private static byte[] MD5(String seed) {
		MessageDigest md;
		StringBuffer sb = null;
		byte[] byteData;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(seed.getBytes());

			byteData = md.digest();

			// convert the byte to hex format method 1
			sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(sb).getBytes();
	}

	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(BLOCKS, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	private static byte[] pad(final byte[] seed) {
		byte[] nseed = new byte[BLOCKS / 8];
		for (int i = 0; i < BLOCKS / 8; i++)
			nseed[i] = 0;
		for (int i = 0; i < seed.length; i++)
			nseed[i] = seed[i];

		return nseed;
	}

	public static byte[] encryptPBE(String password, String cleartext)
			throws Exception {
		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 1024, 256);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		return cipher.doFinal(cleartext.getBytes("UTF-8"));
	}

	public static String decryptPBE(SecretKey secret, String ciphertext,
			byte[] iv) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
		return new String(cipher.doFinal(ciphertext.getBytes()), "UTF-8");
	}

}
