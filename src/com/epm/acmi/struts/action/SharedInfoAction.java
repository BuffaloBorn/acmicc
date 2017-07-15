package com.epm.acmi.struts.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.struts.form.SharedInfoForm;
import com.epm.acmi.util.Connect;
import com.epm.acmi.util.EPMHelper;
import com.epm.acmi.util.OnTabUpdates;


/**
 * Action class for Shared Info Page
 * @author Rajeev Chachra
 */

public class SharedInfoAction extends CCAction {

	private static Logger log = Logger.getLogger(SharedInfoAction.class);
	
	
	/**
	 * Constructor
	 */
	public SharedInfoAction() {
		super();
	}

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

		log.debug("Begin doExecute");
		ctx.forwardToInput();
		
		log.debug("End doExecute");

		}

	
	/**
	 * This Method is called if the Update button on the HTML-Page is pressed.
	 * 
	 * @param ctx
	 *            FormActionContext
	 * @throws Exception
	 */

	public void update_onClick(FormActionContext ctx) throws Exception {
		
		log.debug("Begin update_onClick");
		
		SharedInfoForm sharedInfoForm = (SharedInfoForm) ctx.form();
		String wid = (String)ctx.request().getSession().getAttribute("wid");
	
		Connection conn = Connect.getConnection();
		
		User user  = (User)ctx.session().getAttribute(com.epm.acmi.struts.Constants.loggedUser);
		EPMHelper epmHelper = new EPMHelper();
		String gfid = epmHelper.getGFIDFromWorkId(wid, user);
	
/******************************************************************************************************/			
		//RCA	
/******************************************************************************************************/			
		
// 		Fix to issue #67 cmontero 03/28/2006
//		We store the 'isChecked' string to the DB to persist the checked status when the textarea is empty
		
//		if (sharedInfoForm.getRca() != null && !(sharedInfoForm.getRca().trim().equals(""))) {	
			if ((sharedInfoForm.getRca() != null) 
					&& (!sharedInfoForm.getRca().trim().equals("") || sharedInfoForm.isCheckboxRCA() ) ) {	
				String sqlData = null;
			if (sharedInfoForm.isCheckboxRCA() && sharedInfoForm.getRca().trim().equals("")){
				sqlData = "isChecked";
			} else
				sqlData = sharedInfoForm.getRca();
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "RCA  ");
				updateRowsCount = psmtUpdate.executeUpdate();
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) " ;
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "RCA  ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
				}
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
		 }
		if (sharedInfoForm.getRca().trim().equals("") && !sharedInfoForm.isCheckboxRCA()) {
			try {
				//sharedInfoForm.setCheckboxRCA(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "RCA  ");
				psmtDelete.executeUpdate();
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
		}

		
/******************************************************************************************************/			
		//ADEC
/******************************************************************************************************/			

		//		if (sharedInfoForm.getAdec() != null && !(sharedInfoForm.getAdec().trim().equals(""))) {	
		//		 Fix to issue #67 cmontero 03/28/2006
			if ((sharedInfoForm.getAdec() != null) 
					&& (!sharedInfoForm.getAdec().trim().equals("") || sharedInfoForm.isCheckboxADEC() ) ) {	
				String sqlData = null;
			if (sharedInfoForm.isCheckboxADEC() && sharedInfoForm.getAdec().trim().equals("")){
				sqlData = "isChecked";
			} else
				sqlData = sharedInfoForm.getAdec();
			//if (sharedInfoForm.getAdec() != null && !(sharedInfoForm.getAdec().trim().equals(""))) {			
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "ADEC ");
				updateRowsCount = psmtUpdate.executeUpdate();
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) " ;
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "ADEC ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
				}
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
		 }
			if (sharedInfoForm.getAdec().trim().equals("") && !sharedInfoForm.isCheckboxADEC()) {
				try {
//		if (sharedInfoForm.getAdec().trim().equals("")) {
//			try {
//				sharedInfoForm.setCheckboxADEC(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "ADEC ");
				psmtDelete.executeUpdate();
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
		}
		
			
/******************************************************************************************************/			
		//RPR	
/******************************************************************************************************/			
			
		//		if (sharedInfoForm.getRpr() != null && !(sharedInfoForm.getRpr().trim().equals(""))) {	
		//		 Fix to issue #67 cmontero 03/28/2006
			if ((sharedInfoForm.getRpr() != null) 
					&& (!sharedInfoForm.getRpr().trim().equals("") || sharedInfoForm.isCheckboxRPR() ) ) {	
				String sqlData = null;
			if (sharedInfoForm.isCheckboxRPR() && sharedInfoForm.getRpr().trim().equals("")){
				sqlData = "isChecked";
			} else
				sqlData = sharedInfoForm.getRpr();
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "RPR  ");
				updateRowsCount = psmtUpdate.executeUpdate();
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) " ;
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "RPR  ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
				}
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
		 }
		if (sharedInfoForm.getRpr().trim().equals("") && !sharedInfoForm.isCheckboxRPR()) {
			try {
//		if (sharedInfoForm.getRpr().trim().equals("")) {
//			try {
//				sharedInfoForm.setCheckboxRPR(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "RPR  ");
				psmtDelete.executeUpdate();
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
		}
		

		
		
/******************************************************************************************************/			
		//DNP	
/******************************************************************************************************/			
		
		//		if (sharedInfoForm.getDnp() != null && !(sharedInfoForm.getDnp().trim().equals(""))) {		
		//		 Fix to issue #67 cmontero 03/28/2006
		
		if ((sharedInfoForm.getDnp() != null) 
				&& (!sharedInfoForm.getDnp().trim().equals("") || sharedInfoForm.isCheckboxDNP() ) ) {	
			String sqlData = null;
		if (sharedInfoForm.isCheckboxDNP() && sharedInfoForm.getDnp().trim().equals("")){
			sqlData = "isChecked";
		} else
			sqlData = sharedInfoForm.getDnp();
		
			
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "DNP  ");
				updateRowsCount = psmtUpdate.executeUpdate();
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) " ;
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "DNP  ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
				}
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
		 }
		if (sharedInfoForm.getDnp().trim().equals("") && !sharedInfoForm.isCheckboxDNP()) {
			try {

//		if (sharedInfoForm.getDnp().trim().equals("")) {
//			try {
//				sharedInfoForm.setCheckboxDNP(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "DNP  ");
				psmtDelete.executeUpdate();
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
		}
		
		
/******************************************************************************************************/
		//SIOT	
/******************************************************************************************************/
		//		if (sharedInfoForm.getDnp() != null && !(sharedInfoForm.getDnp().trim().equals(""))) {		
		//		 Fix to issue #67 cmontero 03/28/2006
		
		if ((sharedInfoForm.getSiot() != null) 
				&& (!sharedInfoForm.getSiot().trim().equals("") || sharedInfoForm.isCheckboxSIOT() ) ) {	
			String sqlData = null;
		if (sharedInfoForm.isCheckboxSIOT() && sharedInfoForm.getSiot().trim().equals("")){
			sqlData = "isChecked";
		} else
			sqlData = sharedInfoForm.getSiot();
		
			
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "SIOT ");
				updateRowsCount = psmtUpdate.executeUpdate();
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) " ;
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "SIOT ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
				}
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
		 }
		if (sharedInfoForm.getSiot().trim().equals("") && !sharedInfoForm.isCheckboxSIOT()) {
			try {

//		if (sharedInfoForm.getDnp().trim().equals("")) {
//			try {
//				sharedInfoForm.setCheckboxDNP(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "SIOT  ");
				psmtDelete.executeUpdate();
			}
			catch (Exception e ) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " . GFID = " + gfid );
				e.printStackTrace();
			}
		}

			conn.close();
			log.debug("End update_onClick");
			
			/*
			 *  Added for USR 8399-1 changes
			 *  on 02-12-2008 by Nagrathna Hiriyurkar  
			 */
		
			OnTabUpdates.SharedInfoTabDataStatus(ctx);
			
			// End of USR 8399-1 .

			//ctx.forwardToInput();
			ctx.forwardByName("success");
		}
	}	
