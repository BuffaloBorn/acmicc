package com.cc.acmi.service.servlet;

import java.text.MessageFormat;
import java.util.Locale;

import javax.servlet.jsp.PageContext;

import com.cc.framework.adapter.struts.StrutsFrameworkAdapter;
import com.cc.framework.ui.painter.PainterFactory;

public class AppStrutsFrameworkAdapter extends StrutsFrameworkAdapter  {
	
	public AppStrutsFrameworkAdapter() {
	}

	public String localizeKey(PageContext pageContext, String resourceKey, Locale locale, boolean returnNull) {
		String localized = super.localizeKey(pageContext, resourceKey, locale, returnNull);
		if (localized != null && resourceKey.startsWith("base.")) {
			PainterFactory factories[] = PainterFactory.getFactoyStack(pageContext);
			String curFactory = factories[0].getFactoryId();
			localized = MessageFormat.format(localized, new Object[] { curFactory });
		}
		return localized;
	}


}
