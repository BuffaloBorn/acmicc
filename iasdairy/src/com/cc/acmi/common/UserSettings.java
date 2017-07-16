package com.cc.acmi.common;

import java.io.Serializable;
import java.util.Locale;

/**
 * UserSettings class has User Locale Settings. This class is Common Controls specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.4 $
 */

public class UserSettings implements Serializable {

	private static final long serialVersionUID = 0xcaa62bc9f6e82629L;

	private Locale locale;

	public UserSettings() {
		locale = Locale.ENGLISH;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
