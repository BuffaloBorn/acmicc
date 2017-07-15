package com.epm.acmi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class EncryptPRODPasswords {

	// Log4J logger
	private static Logger log = Logger.getLogger(EncryptPRODPasswords.class);
	
	/**
	 * Do not delete this method. This is the method the administrator uses to
	 * change the passwords in the production environment.
	 * 
	 * @param args:
	 *            first argument is the mode, second argument is, depending on
	 *            the mode, either a value to encrypt/decrypt, or a properties
	 *            file name whose values need to be encrypted or decrypted.
	 */
	public static void main(String[] args)
	{
		try {
			if ((args == null) || (args.length < 2))
				throw new EncryptionException("No valid arguments in main()", null);
			else if (args[0].equalsIgnoreCase("e"))
			{
				PasswordEncryption encryptor = new PasswordEncryption();
				String encryptedStr = encryptor.encrypt(args[1]);
				log.info("Initial String = ##" + args[1] + "## - Encrypted String = ##" + encryptedStr + "##");
			} else if (args[0].equalsIgnoreCase("d"))
			{
				PasswordEncryption encryptor = new PasswordEncryption();
				String decryptedStr = encryptor.decrypt(args[1]);
				log.info("Initial String = ##" + args[1] + "## - Decrypted String = ##" + decryptedStr + "##");
			}else if (args[0].equalsIgnoreCase("ef"))
			{
				String fileName = args[1];
				String newFileName = getNewFileName(fileName, "_Encrypted");
				File file = new File(fileName);
				File newFile = new File(newFileName);

				Properties props = new Properties();
				props.load(new FileInputStream(file.getCanonicalPath()));
				
				Properties encryptedProps = encryptProperties(props);
				encryptedProps.store(new FileOutputStream(newFile.getCanonicalPath()), "Encrypted Properties");
			}else if (args[0].equalsIgnoreCase("df"))
			{
				String fileName = args[1];
				String newFileName = getNewFileName(fileName, "_Decrypted");
				File file = new File(fileName);
				File newFile = new File(newFileName);
				
				Properties props = new Properties();
				props.load(new FileInputStream(file.getCanonicalPath()));
				
				Properties decryptedProps = decryptProperties(props);
				decryptedProps.store(new FileOutputStream(newFile.getCanonicalPath()), "Decrypted Properties");
			}else if (args[0].equalsIgnoreCase("?"))
			{
				printUsageToConsole();
			}
		} catch (Exception e) {
			String exceptionString = "PasswordEncryption.main(): Exception "
					+ e.getClass().getName() + " thrown with message: "
					+ e.getMessage();
			log.debug(exceptionString);
		}
	}
	
	/**
	 * Encrypts the values of a map of properties
	 * 
	 * @param props
	 * @return a Properties object with encrypted values
	 * @throws Exception
	 */
	private static Properties encryptProperties(Properties props) throws Exception
	{
		PasswordEncryption encryptor = new PasswordEncryption();
		Properties newProps = new Properties();
		Iterator e = props.entrySet().iterator();
		
		while (e.hasNext())
		{			
			Map.Entry entry = (Map.Entry)e.next();
			String decryptedValue = encryptor.encrypt(entry.getValue().toString());
			newProps.setProperty(entry.getKey().toString(), decryptedValue);
		}
		
		return newProps;		
	}

	/**
	 * Decrypts the values of a map of properties
	 * 
	 * @param props
	 * @return a Properties object with decrypted values
	 * @throws Exception
	 */
	private static Properties decryptProperties(Properties props) throws Exception
	{
		PasswordEncryption encryptor = new PasswordEncryption();
		Properties newProps = new Properties();
		Iterator e = props.entrySet().iterator();
		
		while (e.hasNext())
		{			
			Map.Entry entry = (Map.Entry)e.next();
			String decryptedValue = encryptor.decrypt(entry.getValue().toString());
			newProps.setProperty(entry.getKey().toString(), decryptedValue);
		}
		
		return newProps;
	}
	
	/**
	 * Returns a new file name. It appends the suffix to the name, before the extension.
	 * 
	 * @param fileName
	 * @param suffixName
	 * @return the new file name: [fileName (witn no extension)][suffixName].[extension]
	 */
	private static String getNewFileName(String fileName, String suffixName)
	{
		StringBuffer newFileName = new StringBuffer(fileName.substring(0, fileName.indexOf('.')));
		newFileName.append(suffixName);
		newFileName.append(fileName.substring(fileName.indexOf('.'), fileName.length()));
		return newFileName.toString();
	}
		
	/**
	 * Prints the main() usage to the console.
	 */
	private static void printUsageToConsole()
	{
		System.out.println("PasswordEncryption - Usage:");
		System.out.println("");
		System.out.println("This app is used with two parameters. The first parameter is the mode.");
		System.out.println("The second parameter can be a value or a properties file, depending on the mode.");
		System.out.println("");
		System.out.println("If the mode is e (encrypt string), the second parameter is the value to encrypt.");
		System.out.println("The program prints the initial string and the encrypted string");
		System.out.println("");
		System.out.println("If the mode is d (decrypt string), the second parameter is the value to decrypt.");
		System.out.println("The program prints the initial string and the decrypted string");
		System.out.println("");
		System.out.println("If the mode is ef (encrypt properties file), the second parameter is the properties file to encrypt");
		System.out.println("The program encrypts the properties file values, and stores them in a new file, with suffix _Encrypted");
		System.out.println("");
		System.out.println("If the mode is df (decrypt properties file), the second parameter is the properties file to decrypt");
		System.out.println("The program decrypts the properties file values, and stores them in a new file, with suffix _Decrypted");
	}	
}
