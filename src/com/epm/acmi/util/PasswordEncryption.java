package com.epm.acmi.util;

import java.security.Security;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * PasswordEncryption is a class that encrypts and decrypts passwords for ACMI
 * based on a fixed encryption algorithm (Password-Based-Encryption with MD5 and
 * DES).
 * 
 * I uses a salt based on prime numbers, and a fixed default passphrase, which
 * was random generated once on 3/29/2007. It is based on a message in a Java
 * encryption blog.
 * 
 * @author Cesar Cardenas
 * @version 1.0 3-29-2007
 */
public class PasswordEncryption {
	// Constants
	private static final String DEFAULT_ENCODING = "UTF-8";

	//The basis for the encryption. Generated once at design time.
	private static final String DEFAULT_PASSPHRASE = "EVFGOjjCnLgvm";

	//Used for mixing with the pass-phrase
	private static final byte[] DEFAULT_SALT = { 0x13, 0x17, 0x19, 0x31, 0x53,
			0x71, 0x07, 0x73 };

	private static final int DEFAULT_ITERATION_COUNT = 9;

	private static final String DEFAULT_ALGORITHM = "PBEWithMD5AndDes";

	// Private attributes
	private String characterEncoding;

	private Cipher encryptCipher;

	private Cipher decryptCipher;

	private BASE64Encoder base64Encoder = new BASE64Encoder();

	private BASE64Decoder base64Decoder = new BASE64Decoder();

	// Log4J logger
	private static Logger log = Logger.getLogger(PasswordEncryption.class);

	// Static Initialization
	{
		// Make sure that SUN is a valid provider
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

	/**
	 * Default Creator. Creation of an instance with default initialization
	 * values
	 * 
	 * @throws EncryptionException
	 */
	public PasswordEncryption() throws EncryptionException {
		this(DEFAULT_PASSPHRASE, DEFAULT_SALT, DEFAULT_ITERATION_COUNT,
				DEFAULT_ENCODING, DEFAULT_ALGORITHM);
	}

	/**
	 * Full parameter creator
	 * 
	 * @param passphrase
	 * @param salt
	 * @param iterationCount
	 * @param characterEncoding
	 * @param algorithm
	 * @throws EncryptionException
	 */
	public PasswordEncryption(String passphrase, byte[] salt,
			int iterationCount, String characterEncoding, String algorithm)
			throws EncryptionException {
		log.debug("PasswordEncryption Creator: Start");
		creationPrecondition(passphrase, salt, iterationCount,
				characterEncoding, algorithm);
		log.debug("PasswordEncryption Creator: passed pre-conditions");

		try {
			PBEParameterSpec params = new PBEParameterSpec(salt, iterationCount);

			// Mix the passphrase with iterationCount times with the use of the
			// salt, to get the key.
			KeySpec keySpec = new PBEKeySpec(passphrase.toCharArray());
			SecretKey key = SecretKeyFactory.getInstance(algorithm, "SunJCE")
					.generateSecret(keySpec);

			this.characterEncoding = characterEncoding;

			// Initialize cyphers with SAME PARAMETERS, to make decryption
			// possible...
			this.encryptCipher = Cipher.getInstance(algorithm, "SunJCE");
			this.encryptCipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key,
					params);

			this.decryptCipher = Cipher.getInstance(algorithm, "SunJCE");
			this.decryptCipher.init(javax.crypto.Cipher.DECRYPT_MODE, key,
					params);
			log.debug("PasswordEncryption Creator: End");
		} catch (Exception e) {
			String exceptionString = "PasswordEncryption Creator: Exception "
					+ e.getClass().getName() + " thrown with message: "
					+ e.getMessage();
			log.debug(exceptionString);
			throw new EncryptionException(exceptionString, e);
		}
	}

	/**
	 * Makes sure all creation parameters are within bounds...
	 * 
	 * @param passphrase
	 * @param salt
	 * @param iterationCount
	 * @param characterEncoding
	 * @param algorithm
	 * @throws EncryptionException
	 */
	private void creationPrecondition(String passphrase, byte[] salt,
			int iterationCount, String characterEncoding, String algorithm)
			throws EncryptionException {
		if (passphrase == null)
			throw new EncryptionException(
					"PasswordEncryption.creationPrecondition(): passphrase is null",
					null);

		if (passphrase.length() < 6)
			throw new EncryptionException(
					"PasswordEncryption.creationPrecondition(): passphrase length is less than six",
					null);

		if (salt == null)
			throw new EncryptionException(
					"PasswordEncryption.creationPrecondition(): salt is null",
					null);

		if (salt.length != 8)
			throw new EncryptionException(
					"PasswordEncryption.creationPrecondition(): salt length is not eight",
					null);

		if (iterationCount < 6 || iterationCount > 20)
			throw new EncryptionException(
					"PasswordEncryption.creationPrecondition(): iteration count is not between siz and 20",
					null);

		if (characterEncoding == null)
			throw new EncryptionException(
					"PasswordEncryption.creationPrecondition(): no character encoding given",
					null);
	}

	/**
	 * Encrypts the string based on the cypher creation parameters. Note: the
	 * use of base64Encoder is necessary for ensuring the String to/from byte[]
	 * transformations are consistent with each other.
	 * 
	 * @param dataString
	 * @return encrypted string
	 * @throws EncryptionException
	 */
	synchronized public String encrypt(String dataString)
		throws EncryptionException {
		log.debug("PasswordEncryption.encrypt(): Start");

		if (dataString == null)
			throw new EncryptionException(
					"PasswordEncryption.encrypt(): dataString is null", null);

		log.debug("PasswordEncryption.encrypt(): passed pre-condition");

		try {
			byte[] dataStringBytes = dataString.getBytes(characterEncoding);
			byte[] encryptedDataStringBytes = encryptCipher
					.doFinal(dataStringBytes);
			String encodedEncryptedDataString = base64Encoder
					.encode(encryptedDataStringBytes);
			log.debug("PasswordEncryption.encrypt(): End");
			return encodedEncryptedDataString;
		} catch (Exception e) {
			String exceptionString = "PasswordEncryption.encrypt(): Exception "
					+ e.getClass().getName() + " thrown with message: "
					+ e.getMessage();
			log.debug(exceptionString);
			throw new EncryptionException(exceptionString, e);
		}
	}

	/**
	 * Decrypts the string based on the cypher creation parameters.
	 * 
	 * @param encodedEncryptedDataString
	 * @return decrypted string
	 * @throws EncryptionException
	 */
	synchronized public String decrypt(String encodedEncryptedDataString)
			throws EncryptionException {
		log.debug("PasswordEncryption.decrypt(): Start");

		if (encodedEncryptedDataString == null)
			throw new EncryptionException(
					"PasswordEncryption.decrypt(): encodedEncryptedDataString is null",
					null);

		log.debug("PasswordEncryption.decrypt(): passed pre-condition");

		try {
			byte[] encryptedDataStringBytes = base64Decoder
					.decodeBuffer(encodedEncryptedDataString);
			byte[] dataStringBytes = decryptCipher
					.doFinal(encryptedDataStringBytes);
			String recoveredDataString = new String(dataStringBytes,
					characterEncoding);
			log.debug("PasswordEncryption.decrypt(): End");
			return recoveredDataString;
		} catch (Exception e) {
			String exceptionString = "PasswordEncryption.decrypt(): Exception "
					+ e.getClass().getName() + " thrown with message: "
					+ e.getMessage();
			log.debug(exceptionString);
			throw new EncryptionException(exceptionString, e);
		}
	}
	
	/**
	 * Helper method to get passwords based on property value for user name.
	 *  
	 * @param pUserName
	 * @return
	 */
	public static String getDecryptedPassword(String pUserName) {
		String pPassword = "";
		try {
			PasswordEncryption passwordEncryption = new PasswordEncryption();
			if (pUserName.equalsIgnoreCase(LocalProperties
					.getProperty("ADMIN_USER")))
				pPassword = passwordEncryption.decrypt(LocalProperties
						.getProperty("ADMIN_PWD"));
			else if (pUserName.equalsIgnoreCase(LocalProperties
					.getProperty("username")))
				pPassword = passwordEncryption.decrypt(LocalProperties
						.getProperty("password"));
			else if (pUserName.equalsIgnoreCase(LocalProperties
					.getProperty("stellentUser")))
				pPassword = passwordEncryption.decrypt(LocalProperties
						.getProperty("stellentPassword"));
			else if (pUserName.equalsIgnoreCase(LocalProperties
					.getProperty("SUSPEND_USER")))
				pPassword = passwordEncryption.decrypt(LocalProperties
						.getProperty("SUSPEND_PWD"));
			else if (pUserName.equalsIgnoreCase(LocalProperties
					.getProperty("SECURITY_PRINCIPAL")))
				pPassword = passwordEncryption.decrypt(LocalProperties
						.getProperty("SECURITY_CREDENTIALS"));
		} catch (EncryptionException e) {
			log.error("Invalid Password for Username : " + pUserName);
		}
		
		return pPassword;
	}
	
	/**
	 * Testing the PasswordEncryption class This is the way we would use it
	 * within ACMICC and ACMIEPM
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PasswordEncryption dataStringEncryptAgent = new PasswordEncryption();

			// Get the dataString to encrypt from the command line
			String dataString = (args.length == 0) ? "This is my message."
					: args[0];
			log.debug("Data string ....................[" + dataString + "]");

			// Encrypt the data
			String encodedEncryptedDataString = dataStringEncryptAgent
					.encrypt(dataString);
			log.debug("Encoded encrypted data string ..["
					+ encodedEncryptedDataString + "]");

			// Decrypt the data
			String recoveredDataString = dataStringEncryptAgent
					.decrypt(encodedEncryptedDataString);
			log.debug("Recovered data string ..........[" + recoveredDataString
					+ "]");
		} catch (Exception e) {
			String exceptionString = "PasswordEncryption.main(): Exception "
					+ e.getClass().getName() + " thrown with message: "
					+ e.getMessage();
			log.debug(exceptionString);
		}
	}
}
