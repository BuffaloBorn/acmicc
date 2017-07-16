package com.epm.acmi.struts.form;

import java.util.Locale;

import com.cc.acmi.common.Language;
import com.cc.framework.adapter.struts.FWActionForm;
import com.cc.framework.ui.painter.def.DefPainterFactory;

/**
 * Logon Form Action Class
 * 
 * @author Jay Hombal
 */
public class LogonActionForm extends FWActionForm {

	private static final long serialVersionUID = 0xfd9b1ed012f2bfe2L;

	private String userId;

	private String password;

	private String uiType;

	private String language;

	private Language languageOptions;

	public LogonActionForm() {
		uiType = DefPainterFactory.instance().getFactoryId();
		language = Language.ENGLISCH.getKey();
		languageOptions = Language.NONE;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId.toUpperCase();
	}

	public String getUiType() {
		return uiType;
	}

	public void setUiType(String string) {
		uiType = string;
	}

	public String getLanguage() {
		return language;
	}

	public Language getLanguageOptions() {
		return languageOptions;
	}

	public void setLanguage(String string) {
		language = string;
	}

	public Locale getLocale() {
		return Language.toLocale(language);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
