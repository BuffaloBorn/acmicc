package com.epm.acmi.struts.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.CaseNotesForm;
import com.epm.acmi.util.Connect;
import com.epm.acmi.util.EPMHelper;

/**
 * CaseNotesAction handles Case Notes page.
 * 
 * @author Rajeev Chachra
 */

public class CaseNotesAction extends CCAction {

	private static Logger log = Logger.getLogger(CaseNotesAction.class);


	/**
	 * Constructor
	 */
	public CaseNotesAction() {
		super();
	}


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
		log.debug("end doExecute");
	}


	/**
	 * Handles Save Button event
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void save_onClick(FormActionContext ctx) throws Exception {

		log.debug("Begin save_onClick");
		String action ="save_onClick";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rstRefresh = null;
		PreparedStatement psmtRefresh = null;
		String gfid = null;
		try {
			CaseNotesForm caseNotesForm = (CaseNotesForm) ctx.form();
			String wid = (String) ctx.request().getSession().getAttribute("wid");

			conn = Connect.getConnection();
			if (conn != null) {
				User user = (User) ctx.session().getAttribute(com.epm.acmi.struts.Constants.loggedUser);
				EPMHelper epmHelper= new EPMHelper();
				
				//Get gfid from session
				gfid = (String) ctx.session().getAttribute((Constants.gfid));
				
				if (gfid == null) {
					gfid = epmHelper.getGFIDFromWorkId(wid, user);
				}

				String message = caseNotesForm.getMessage();
				String subject = caseNotesForm.getSubject();
				String sql = " insert into IUACaseNotes(GFID, LastUpdatedDateTime, subject, LastUpdatedUserId, message) values (?, GetDate(), ?, ?, ? )";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, gfid);
				psmt.setString(2, subject.replaceAll("&nbsp;", " "));
				psmt.setString(3, user.getUserName());
				psmt.setString(4, message.replaceAll("&nbsp;", " "));

				if (!((message == null || message.trim().equals("")) && (subject == null || subject.trim().equals("")))) {
					psmt.execute();
				}

				String sqlRefresh = " select * from IUACaseNotes WITH (NOLOCK) where GFID = ? ";
				psmtRefresh = conn.prepareStatement(sqlRefresh);
				psmtRefresh.setString(1, gfid);
				rstRefresh = psmtRefresh.executeQuery();
				String previouNotes = "";

				String format = "M/d/yyyy h:m a";
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);

				while (rstRefresh.next()) {
					Timestamp toFormatTime = rstRefresh.getTimestamp("LastUpdatedDateTime");
					previouNotes = previouNotes + rstRefresh.getString("LastUpdatedUserId");
					previouNotes = previouNotes + " : " + sdf.format(toFormatTime);
					;
					previouNotes = previouNotes + " : Sub : " + rstRefresh.getString("subject");
					previouNotes = previouNotes + " : Message - " + rstRefresh.getString("message");
					previouNotes = previouNotes + "\n\n";
				}
				caseNotesForm.setPreviousNotes(previouNotes);
				caseNotesForm.setMessage("");
				caseNotesForm.setSubject("");
				ctx.session().setAttribute("CaseNotesForm", caseNotesForm);
			}
			log.debug("End save_onClick");

		} catch (Exception e) {
			log.error("Exception: Failed to save case notes, GFID = " + gfid, e);

		} finally {
			if (psmt != null)
				psmt.close();
			if (rstRefresh != null)
				rstRefresh.close();
			if (psmtRefresh != null)
				psmtRefresh.close();
			if (conn != null)
				conn.close();
		}
		/*
		 * USR 8399-1 changes 
		 * on 02-15-2008 by Nagrathna Hiriyurkar 
		 */
			ctx.forwardToAction("main/iuauser?ctrl=nestedtabset&action=TabClick&param=tab14");
        // End of USR 8399-1 changes 
	}
	 /*
	 * USR 8399-1 changes 
	 * on 02-15-2008 by Nagrathna Hiriyurkar 
	 */
	public void clear_onClick(FormActionContext ctx) throws Exception {
		ctx.forwardToAction("main/iuauser?ctrl=nestedtabset&action=TabClick&param=tab14");
	    } 
 	// End of USR 8399-1 changes

}
