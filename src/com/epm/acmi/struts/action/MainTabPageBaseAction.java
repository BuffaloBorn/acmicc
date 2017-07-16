package com.epm.acmi.struts.action;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.ui.control.TabsetControl;

/**
 * $Header: /usr/local/cvsroot/acmiroot/acmicc/src/com/epm/acmi/struts/action/MainTabPageBaseAction.java,v 1.8 2006/05/02 14:55:40 jhombal Exp $
 * $Revision: 1.8 $
 * $Date: 2006/05/02 14:55:40 $
 *
 * ==========================================================================
 *
 * Copyright (c) 2002-2003 SCC Informationssysteme GmbH. All rights reserved.
 * www.scc-gmbh.com
 *
 * ==========================================================================
 */

/**
 * TabPageBaseAction - Common control specific class to handle tab page click events
 * 
 * @author Jay Hombal
 * @version $Revision: 1.8 $
 */

public abstract class MainTabPageBaseAction extends CCAction {

	/** Identifier TabPage1 */
	protected static final String TABPAGE1 = "tab1";

	/** Identifier TabPage2 */
	protected static final String TABPAGE2 = "tab2";

	/** Identifier TabPage3 */
	protected static final String TABPAGE3 = "tab3";

	/** Identifier TabPage4 */
	protected static final String TABPAGE4 = "tab4";
	
	/** Identifier TabPage5 */
	protected static final String TABPAGE5 = "tab5";
	
	/** Identifier TabPage5 */
	protected static final String TABPAGE6 = "tab6";
	
	/** Identifier TabPage5 */
	protected static final String TABPAGE7 = "tab7";
		
	/** Identifier TabPage5 */
	protected static final String TABPAGE8 = "tab8";


	/** Identifier TabPage11 */
	protected static final String TABPAGE11 = "tab11";

	/** Identifier TabPage12 */
	protected static final String TABPAGE12 = "tab12";

	/** Identifier TabPage13 */
	protected static final String TABPAGE13 = "tab13";

	/** Identifier TabPage14 */
	protected static final String TABPAGE14 = "tab14";

	/** Identifier TabPage111 */
	protected static final String TABPAGE111 = "tab111";

	/** Identifier TabPage112 */
	protected static final String TABPAGE112 = "tab112";

	/** Identifier TabPage113 */
	protected static final String TABPAGE113 = "tab113";

	/** Identifier TabPage114 */
	protected static final String TABPAGE114 = "tab114";

	/** Identifier TabPage115 */
	protected static final String TABPAGE115 = "tab115";
	
	/** Identifier TabPage120 */
	protected static final String TABPAGE120 = "tab120";
	
	/** Identifier TabPage121 */
	protected static final String TABPAGE121 = "tab121";
	

	/**
	 * @see com.cc.framework.adapter.struts.FrameworkAction#doExecute(com.cc.framework.adapter.struts.ActionContext)
	 */
	
	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) throws Exception {
		try {
			TabsetControl tsctrl = (TabsetControl) ctx.session().getAttribute("secondarymaintabset");
			tsctrl.setSelectedTab(getTabPageId());
			System.err.println(getTabPageId());
			if (getTabPageId().equals("tab2")) {
				ctx.session().removeAttribute("processList");
			}
		} catch (Throwable t) {
			ctx.addGlobalError("error.mainError", "");
			log.error("Exception " + t.getClass().getName() + " caught with message: " + t.getMessage());
        	t.printStackTrace();
		}

		ctx.forwardToInput();
	}

	/**
	 * Returns the Id for the Tabpage
	 * 
	 * @return String TabPageId
	 */
	public abstract String getTabPageId();

}
