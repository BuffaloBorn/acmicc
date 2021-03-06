package com.epm.acmi.struts.action;

import com.cc.framework.adapter.struts.ActionContext;

/**
 *  Common Control specific class, Extracted from the framework sample
 * @author Jay Hombal
 *
 */
public class CCResetAction extends CCAction
{

    public CCResetAction()
    {
    }

	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
    public void doExecute(ActionContext ctx)
        throws Exception
    {
        try
        {
            log.debug("Reset DataBase...");
        }
        catch(Throwable t)
        {
            log.error(t);
        }
    }
}
