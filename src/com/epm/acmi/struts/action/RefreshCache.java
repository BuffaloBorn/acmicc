package com.epm.acmi.struts.action;

import org.apache.log4j.Logger;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.struts.form.AdminForm;
import com.epm.acmi.util.*;

public class RefreshCache extends CCAction {
	
	protected static Logger log = Logger.getLogger(RefreshCache.class);
	
	public void doExecute(ActionContext context) {
		
		context.forwardToInput();
	}
	
	public void ok_onClick(FormActionContext context) throws Exception {
		//Need to figure out which cache is being refreshed.
		//Get values from form
		AdminForm form = (AdminForm)context.form();
		
		String acmicCache = form.getRefreshAcmic();
		String epmCache   = form.getRefreshEPM();
		String iasCache = form.getRefreshIAS();
		
		try {
			if (acmicCache != null && acmicCache.equalsIgnoreCase("true")) {
				log.debug("Getting ready to Refresh ACMIC Cache");
				ACMICache.refreshCache();
				log.debug("Finished Refreshing ACMIC Cache");
				context.addGlobalMessage("refresh.acmic.success");
				form.setRefreshAcmic(null);
			}
		} catch(Exception e){
			context.addGlobalError("refersh.acmic.error");
			log.error(e);
		}
		
		try {
			if (iasCache != null && iasCache.equalsIgnoreCase("true")) {
				log.debug("Getting ready to Refresh IAS Cache");
				ACMICache.getIasCodesTable();
				log.debug("Finished Refreshing IAS Cache");
				context.addGlobalMessage("refresh.ias.success");
				form.setRefreshIAS(null);
			}
		} catch(Exception e){
			context.addGlobalError("refersh.ias.error");
			log.error(e);
		}
		
			
		try {
			if (epmCache != null && epmCache.equalsIgnoreCase("true")) {
				log.debug("Getting ready to Refresh EPM Cache");
				
				EPMHelper epm = new EPMHelper();
	
				epm.synchronizeEPMLDAPUserCache();
				epm.synchronizeEPMLDAPGroupCache();
				
				log.debug("Finished Refreshing EPM Cache");
				form.setRefreshEPM(null);
				context.addGlobalMessage("refresh.epm.success");
			}	
		} catch (Exception e) {
			context.addGlobalError("refersh.epm.error");
			log.error(e);
		}

		context.forwardToInput();
	}
	
	public void cancel_onClick(ActionContext context) {
		context.forwardByName("success");
	}
}
