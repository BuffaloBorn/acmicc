package com.epm.acmi.struts.action;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

/**
 * Referenced classes of package com.epm.acmi.struts.action: CCAction
 * Common control specific class.Extracted from common control sample app
 * @author Jay Hombal
 */
public class MainAction extends CCAction {

	public MainAction() {
	}

	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) throws Exception {
		ctx.forwardToInput();
	}

	/**
	 * Back Button click event
	 */
	public void back_onClick(FormActionContext ctx) throws Exception {
		ctx.forwardByName("back");
	}
}
