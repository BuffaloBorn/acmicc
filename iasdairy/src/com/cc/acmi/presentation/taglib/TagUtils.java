package com.cc.acmi.presentation.taglib;

import javax.servlet.jsp.PageContext;

import com.cc.framework.adapter.FrameworkAdapterFactory;
import com.cc.framework.common.Severity;
import com.cc.framework.taglib.template.TemplateHelp;
import com.cc.framework.ui.model.MessageDataModel;


/**
 * TagUtils is Common control specific class. TagUtil Class is used in JSPs 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.4 $
 */
public abstract class TagUtils
{

	
    private TagUtils()
    {
    }

    

    public static String getTemplate(PageContext pageContext, String attribute)
    {
        try
        {
            return TemplateHelp.getTemplate(pageContext, attribute);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public static boolean hasMessages(PageContext pageContext, String severity)
    {
        try
        {
            return hasMessages(pageContext, Severity.parse(severity));
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public static boolean hasMessages(PageContext pageContext, Severity severity)
    {
        try
        {
            MessageDataModel messages = FrameworkAdapterFactory.getAdapter().getMessages(pageContext, severity);
            return messages != null && messages.size() > 0;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    
}
