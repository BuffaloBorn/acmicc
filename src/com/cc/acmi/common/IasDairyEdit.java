package com.cc.acmi.common;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.struts.form.ActivateIASForm;
import com.epm.acmi.util.MiscellaneousUtils;

public class IasDairyEdit {
	
	private static Logger log = MiscellaneousUtils.getIASLogger();
	private static String classAction = "IAS Dairy Edits";
	

	private static boolean UnderWritingStatusErrorEditOne(FormActionContext ctx )
	{
		ActivateIASForm form = (ActivateIASForm) ctx.form();
		
		if ( (form.getUNDERWRITER_OLD().equalsIgnoreCase("i")) && (form.getUdwselectedItem().equalsIgnoreCase("y")) && (form.getAMENDMENT_IND().equalsIgnoreCase("n")))
		{
			
			ctx.addGlobalError(DiaryMessages.UI_EDIT_MSG, "There is no amendment for this policy");
			log.debug("Finish....displaying Underwriting Status Error Edit One " + classAction);
			form.setUdwselectedItem("I");
			return true;
		}
		
		return false;
	}

	private static boolean UnderWritingStatusErrorEditTwo(FormActionContext ctx )
	{
		ActivateIASForm form = (ActivateIASForm) ctx.form();
		
		if(form.getUdwselectedItem().equalsIgnoreCase("s"))
		{
			if((form.getPOLICY_TRANS_TYPE().equalsIgnoreCase("nb") || form.getPOLICY_TRANS_TYPE().equalsIgnoreCase("ld")) && form.getPOLICY_TRANS_TYPE_COND().equalsIgnoreCase("dp") )
			{
				ctx.addGlobalError(DiaryMessages.UI_EDIT_MSG, "Underwriting status can not go back to S after policy us issued and placed.");
				log.debug("Finish....displaying Underwriting Status Error Edit Two " + classAction);
				return true;
			}
		}
		return false;
	}
	
	private static boolean UnderWritingStatusErrorEditThree(FormActionContext ctx )
	{
		ActivateIASForm form = (ActivateIASForm) ctx.form();
		
		if((form.getUdwselectedItem().equalsIgnoreCase("D") || form.getUdwselectedItem().equalsIgnoreCase("Z") || form.getUdwselectedItem().equalsIgnoreCase("Y") || form.getUdwselectedItem().equalsIgnoreCase("B")) && form.getRIDER_IND().equalsIgnoreCase("y"))
		{
			ctx.addGlobalError(DiaryMessages.UI_EDIT_MSG, "Invalid underwriting status when a rider exists");
			log.debug("Finish....displaying Underwriting Status Error Edit Three " + classAction);
			return true;	
		}
		return false;
	}
	
	
	private static boolean UnderWritingStatusErrorEditFour(FormActionContext ctx )
	{
		ActivateIASForm form = (ActivateIASForm) ctx.form();
		
		if(form.getUdwselectedItem().equalsIgnoreCase("y") && form.getAMENDMENT_IND().equalsIgnoreCase("n"))
		{
			ctx.addGlobalError(DiaryMessages.UI_EDIT_MSG, "There is no amendment for this policy");
			log.debug("Finish....displaying Underwriting Status Error Edit Four " + classAction);
			return true;
		}
		return false;
	}
	
	
	private static boolean UnderWritingStatusErrorEditFive(FormActionContext ctx )
	{
		ActivateIASForm form = (ActivateIASForm) ctx.form();
		
		if(form.getUdwselectedItem().equalsIgnoreCase("x") && form.getNUMOFRIDER().toString().equalsIgnoreCase("0"))
		{
			ctx.addGlobalError(DiaryMessages.UI_EDIT_MSG, "Status code X requires 1 rider on policy");
			log.debug("Finish....displaying Underwriting Status Error Edit Five " + classAction);
			return true;
		}
		return false;
	}
	
	private static boolean UnderWritingStatusErrorEditSix( FormActionContext ctx )
	{
		ActivateIASForm form = (ActivateIASForm) ctx.form();
		
		BigDecimal one = new BigDecimal("1");
		
		if(form.getUdwselectedItem().equalsIgnoreCase("x") && (form.getNUMOFRIDER().compareTo(one) == 1))
		{
			ctx.addGlobalError(DiaryMessages.UI_EDIT_MSG, "Status code X requires only 1 rider.");
			log.debug("Finish....displaying Underwriting Status Error Edit Six " + classAction);
			return true;
		}
		return false;
	}
	
	
	private static boolean UnderWritingStatusErrorEditSeven(FormActionContext ctx )
	{
		ActivateIASForm form = (ActivateIASForm) ctx.form();
		
		if((form.getUdwselectedItem().equalsIgnoreCase("d") ||form.getUdwselectedItem().equalsIgnoreCase("t")|| form.getUdwselectedItem().equalsIgnoreCase("w")) && (form.getISSUED_PLACE_FLAG().equalsIgnoreCase("y")))
		{
			ctx.addGlobalError(DiaryMessages.UI_EDIT_MSG, "Cannot update to D, W, or I after policy has been issued and placed.");
			log.debug("Finish....displaying Underwriting Status Error Edit Seven " + classAction);
			return true;
		}
		return false;
	}
	
	public static boolean CallErrorEdits(FormActionContext ctx )
	{
		boolean hitEdit = false;
		
		
		if (UnderWritingStatusErrorEditOne(ctx))
			hitEdit = true;
		
		if (UnderWritingStatusErrorEditTwo(ctx))
			hitEdit = true;
		
		if (UnderWritingStatusErrorEditThree(ctx))
			hitEdit = true;
		
		if (UnderWritingStatusErrorEditFour(ctx))
			hitEdit = true;
		
		if (UnderWritingStatusErrorEditFive(ctx))
			hitEdit = true;
		
		if (UnderWritingStatusErrorEditSix(ctx))
			hitEdit = true;
		
		if (UnderWritingStatusErrorEditSeven(ctx))
			hitEdit = true;
		
		return hitEdit;
	}
	
	
}
