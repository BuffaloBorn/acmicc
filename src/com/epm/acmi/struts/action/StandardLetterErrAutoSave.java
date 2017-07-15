package com.epm.acmi.struts.action;

import com.cc.framework.adapter.struts.ActionContext;

public class StandardLetterErrAutoSave extends CCAction{

	public void doExecute(ActionContext ctx) throws Exception {
		ctx.forwardToInput();
	}

}
