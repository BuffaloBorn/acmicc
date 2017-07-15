package com.epm.acmi.util;

public class EncryptionException extends Exception {
	private static final long serialVersionUID = 6458840453423129598L;

	public EncryptionException(String text, Exception chain) {
		super(text, chain);
	}
}
