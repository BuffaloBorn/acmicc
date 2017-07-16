package com.epm.acmi.struts.action;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.TabsetControl;

public class MainTabSetBrowseAction extends CCAction  {

	/**
	 * This Action creates the TabsetControl and selects the first Page The TabSetControl works with Server Roundtrips. So
	 * each time a TabPage is clicked the correspondig Action is called. Because a Click on a TabPage is an Event on the
	 * TabSetControl the Methode tabsetcontrolname_onTabClick(ControlactionContext ctx, String seltab) is called. For
	 * Details see the TabPageAction's.
	 * 
	 * @author Jay Hombal
	 * @version $Revision: 1.14 $
	 */
	public MainTabSetBrowseAction() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */

	public void doExecute(ActionContext ctx) throws Exception {
		try {
			log.debug("Begin doExecute()");
			TabsetControl maintabset = new TabsetControl();
			ctx.session().setAttribute("maintabset", maintabset);

			TabsetControl secondaryTabSet = new TabsetControl();
			ctx.session().setAttribute("secondarymaintabset", secondaryTabSet);
			secondaryTabSet.setSelectedTab(MainTabPageBaseAction.TABPAGE5);

			TabsetControl nestedtabset = new TabsetControl();
			ctx.session().setAttribute("nestedtabset", nestedtabset);
			nestedtabset.setSelectedTab(MainTabPageBaseAction.TABPAGE11);

			TabsetControl nbwstabset = new TabsetControl();
			ctx.session().setAttribute("nbwstabset", nbwstabset);
			nbwstabset.setSelectedTab(MainTabPageBaseAction.TABPAGE111);

			// In this example we show the first tab of the tabset at startup.
			// Therefore we must make the data for this JSP available.
			// To do this, we forward to the action which manages this
			// tabpage. See the FowardConfig in WEB-INF\config\struts-sample.xml
			// If you can see in the configuration file the TabPage1Action is
			// called.
			ctx.forwardByName(MainTabPageBaseAction.TABPAGE5);
			log.debug("End doExecute()");
		} catch (Exception e) {
			ctx.addGlobalError("error.mainError", "");
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void secondarymaintabset_onTabClick(ControlActionContext ctx, String seltab) throws Exception {

		try {
			log.debug("Begin secondarymaintabset_onTabClick()");
			if (null == ctx.session().getAttribute("secondarymaintabset")) {
				TabsetControl secondaryTabSet = new TabsetControl();
				ctx.session().setAttribute("secondarymaintabset", secondaryTabSet);
				secondaryTabSet.setSelectedTab(MainTabPageBaseAction.TABPAGE5);

			}

			if (seltab.equals(MainTabPageBaseAction.TABPAGE5)) {

//				// Clear Stellent Session Id when user tabs into document exceptions
//				User user = (User) ctx.session().getAttribute(Constants.loggedUser);
//				String AAID = user.getAAID();
//				if (AAID != null) {
//					StellentClient.logout(AAID);
//					user.setAAID(null);
//				}
//				log.debug("trying to forward to WL action");
				ctx.forwardToAction("worklist");
				
			}

			if (seltab.equals(MainTabPageBaseAction.TABPAGE6))
			{
				//ctx.forwardToAction("iasdiary");
				ctx.forwardByName(MainTabPageBaseAction.TABPAGE6);
			}
			
//			if (seltab.equals(MainTabPageBaseAction.TABPAGE3)) {

				// Clear Stellent Session Id when user tabs into document exceptions
//				User user = (User) ctx.session().getAttribute(Constants.loggedUser);
//				String AAID = user.getAAID();
//				if (AAID != null) {
//					StellentClient.logout(AAID);
//					user.setAAID(null);
//				}

//				ReassignTaskForm form = new ReassignTaskForm();
//				HashMap map1 = new HashMap();
//
//				HashMap map = ACMICache.getUsers();
//				Iterator itr = map.keySet().iterator();
//				while (itr.hasNext()) {
//					String key = (String) itr.next();
//					LDAPUser usr = (LDAPUser) map.get(key);
//					map1.put(key, usr.getFirstName() + " " + usr.getLastName());
//				}
//				form.setMap(map1);
//				ctx.session().setAttribute("ReassignTaskForm", form);
//				ctx.control().execute(ctx, seltab);
//
//			}
//			log.debug("End secondarymaintabset_onTabClick()");
		} catch (Exception e) {
			ctx.addGlobalError("error.mainError", "");
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage());
			e.printStackTrace();
		}
	}


}
