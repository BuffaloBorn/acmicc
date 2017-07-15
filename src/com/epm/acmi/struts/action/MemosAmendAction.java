package com.epm.acmi.struts.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.struts.form.MemosAmendForm;
import com.epm.acmi.util.Connect;
import com.epm.acmi.util.EPMHelper;
import com.epm.acmi.util.OnTabUpdates;

/**
 * Action class for Memos and Amendment Page
 * 
 * @author Rajeev Chachra
 */

public class MemosAmendAction extends CCAction {

	private static Logger log = Logger.getLogger(MemosAmendAction.class);


	/**
	 * Constructor
	 */
	public MemosAmendAction() {
		super();
	}


	/**
	 * @see com.cc.framework.adapter.struts.FrameworkAction#doExecute(com.cc.framework.adapter.struts.ActionContext)
	 */
	public void doExecute(ActionContext ctx) throws Exception {

		// forward to the jsp page
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
		MemosAmendForm memosForm = (MemosAmendForm) ctx.form();
		String wid = (String) ctx.request().getSession().getAttribute("wid");

		Connection conn = Connect.getConnection();

		User user = (User) ctx.session().getAttribute(com.epm.acmi.struts.Constants.loggedUser);
		EPMHelper epmHelper = new EPMHelper();
		String gfid = epmHelper.getGFIDFromWorkId(wid, user);

		/** *************************************************************************************************** */
		// ANCH
		/** *************************************************************************************************** */

		if (memosForm.getAnch() != null && (!(memosForm.getAnch().trim().equals("")) || memosForm.isCheckboxANCH())) {
			String sqlData = null;
			if (memosForm.isCheckboxANCH() && memosForm.getAnch().trim().equals("")) {
				sqlData = "isChecked";
			} else
				sqlData = memosForm.getAnch();
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "ANCH ");
				updateRowsCount = psmtUpdate.executeUpdate();
				psmtUpdate.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) ";
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "ANCH ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
					psmt.close();
				}
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid , e);
				e.printStackTrace();
			}
		}
		if (memosForm.getAnch().trim().equals("") && !memosForm.isCheckboxANCH()) {
			try {
				// memosForm.setCheckboxANCH(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "ANCH ");
				psmtDelete.executeUpdate();
				psmtDelete.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed ,GFID = " + gfid, e);
				e.printStackTrace();
			}
		}

		/** *************************************************************************************************** */
		// AOA
		/** *************************************************************************************************** */

		if (memosForm.getAoa() != null && (!(memosForm.getAoa().trim().equals("")) || memosForm.isCheckboxAOA())) {
			String sqlData = null;
			if (memosForm.isCheckboxAOA() && memosForm.getAoa().trim().equals("")) {
				sqlData = "isChecked";
			} else
				sqlData = memosForm.getAoa();
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "AOA  ");
				updateRowsCount = psmtUpdate.executeUpdate();
				psmtUpdate.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed, GFID = " + gfid , e);
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) ";
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "AOA  ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
					psmt.close();
				}
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed, GFID = " + gfid, e);
				e.printStackTrace();
			}
		}
		if (memosForm.getAoa().trim().equals("") && !memosForm.isCheckboxANCH()) {
			try {
				// memosForm.setCheckboxAOA(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "AOA  ");
				psmtDelete.executeUpdate();
				psmtDelete.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed, GFID = " + gfid , e);
				e.printStackTrace();
			}
		}

		/** *************************************************************************************************** */
		// GAL
		/** *************************************************************************************************** */

		if (memosForm.getGal() != null && (!(memosForm.getGal().trim().equals("")) || memosForm.isCheckboxGAL())) {
			String sqlData = null;
			if (memosForm.isCheckboxGAL() && memosForm.getGal().trim().equals("")) {
				sqlData = "isChecked";
			} else
				sqlData = memosForm.getGal();
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "GAL  ");
				updateRowsCount = psmtUpdate.executeUpdate();
				psmtUpdate.close();

			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) ";
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "GAL  ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
					psmt.close();
				}
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed, GFID = " + gfid, e);
				e.printStackTrace();
			}
		}
		if (memosForm.getGal().trim().equals("") && !memosForm.isCheckboxGAL()) {
			try {
				// memosForm.setCheckboxGAL(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "GAL  ");
				psmtDelete.executeUpdate();
				psmtDelete.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
		}

		/** *************************************************************************************************** */
		// MDI
		/** *************************************************************************************************** */
		if (memosForm.getMdi() != null && (!(memosForm.getMdi().trim().equals("")) || memosForm.isCheckboxMDI())) {
			String sqlData = null;
			if (memosForm.isCheckboxMDI() && memosForm.getMdi().trim().equals("")) {
				sqlData = "isChecked";
			} else
				sqlData = memosForm.getMdi();
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "MDI  ");
				updateRowsCount = psmtUpdate.executeUpdate();
				psmtUpdate.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed, GFID = " + gfid, e);
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) ";
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "MDI  ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
					psmt.close();
				}
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
		}
		if (memosForm.getMdi().trim().equals("") && !memosForm.isCheckboxMDI()) {
			try {
				// memosForm.setCheckboxMDI(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "MDI  ");
				psmtDelete.executeUpdate();
				psmtDelete.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed, GFID = " + gfid, e);
				e.printStackTrace();
			}
		}

		/** *************************************************************************************************** */
		// MOI
		/** *************************************************************************************************** */
		if (memosForm.getMoi() != null && (!(memosForm.getMoi().trim().equals("")) || memosForm.isCheckboxMOI())) {
			String sqlData = null;
			if (memosForm.isCheckboxMOI() && memosForm.getMoi().trim().equals("")) {
				sqlData = "isChecked";
			} else
				sqlData = memosForm.getMoi();
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "MOI  ");
				updateRowsCount = psmtUpdate.executeUpdate();
				psmtUpdate.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed, GFID = " + gfid, e);
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) ";
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "MOI  ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
					psmt.close();
				}
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
		}
		if (memosForm.getMoi().trim().equals("") && !memosForm.isCheckboxMOI()) {
			try {
				// memosForm.setCheckboxMOI(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "MOI  ");
				psmtDelete.executeUpdate();
				psmtDelete.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed, GFID = " + gfid, e);
				e.printStackTrace();
			}
		}

		/** *************************************************************************************************** */
		// MMI
		/** *************************************************************************************************** */
		if (memosForm.getMmi() != null && (!(memosForm.getMmi().trim().equals("")) || memosForm.isCheckboxMMI())) {
			String sqlData = null;
			if (memosForm.isCheckboxMMI() && memosForm.getMmi().trim().equals("")) {
				sqlData = "isChecked";
			} else
				sqlData = memosForm.getMmi();
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "MMI  ");
				updateRowsCount = psmtUpdate.executeUpdate();
				psmtUpdate.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) ";
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "MMI  ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
					psmt.close();
				}
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed, GFID = " + gfid, e);
				e.printStackTrace();
			}
		}
		if (memosForm.getMmi().trim().equals("") && !memosForm.isCheckboxMMI()) {
			try {
				// memosForm.setCheckboxMMI(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "MMI  ");
				psmtDelete.executeUpdate();
				psmtDelete.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
		}

		/** *************************************************************************************************** */
		// SIA
		/** *************************************************************************************************** */
		if (memosForm.getSia() != null && (!(memosForm.getSia().trim().equals("")) || memosForm.isCheckboxSIA())) {
			String sqlData = null;
			if (memosForm.isCheckboxSIA() && memosForm.getSia().trim().equals("")) {
				sqlData = "isChecked";
			} else
				sqlData = memosForm.getSia();
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "SIA  ");
				updateRowsCount = psmtUpdate.executeUpdate();
				psmtUpdate.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) ";
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "SIA  ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
					psmt.close();
				}
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
		}
		if (memosForm.getSia().trim().equals("") && !memosForm.isCheckboxSIA()) {
			try {
				// memosForm.setCheckboxSIA(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "SIA  ");
				psmtDelete.executeUpdate();
				psmtDelete.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
		}

		/** *************************************************************************************************** */
		// SIAD
		/** *************************************************************************************************** */
		if (memosForm.getSiad() != null && (!(memosForm.getSiad().trim().equals("")) || memosForm.isCheckboxSIAD())) {
			String sqlData = null;
			if (memosForm.isCheckboxSIAD() && memosForm.getSiad().trim().equals("")) {
				sqlData = "isChecked";
			} else
				sqlData = memosForm.getSiad();
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "SIAD ");
				updateRowsCount = psmtUpdate.executeUpdate();
				psmtUpdate.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) ";
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "SIAD ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
					psmt.close();
				}
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
		}
		if (memosForm.getSiad().trim().equals("") && !memosForm.isCheckboxSIAD()) {
			try {
				// memosForm.setCheckboxSIAD(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "SIAD ");
				psmtDelete.executeUpdate();
				psmtDelete.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
		}

		/** *************************************************************************************************** */
		// SIAG
		/** *************************************************************************************************** */
		if (memosForm.getSiag() != null && (!(memosForm.getSiag().trim().equals("")) || memosForm.isCheckboxSIAG())) {
			String sqlData = null;
			if (memosForm.isCheckboxSIAG() && memosForm.getSiag().trim().equals("")) {
				sqlData = "isChecked";
			} else
				sqlData = memosForm.getSiag();
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "SIAG ");
				updateRowsCount = psmtUpdate.executeUpdate();
				psmtUpdate.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) ";
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "SIAG ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
					psmt.close();
				}
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: GFID =  "+ gfid + e.getMessage());
				e.printStackTrace();
			}
		}
		if (memosForm.getSiag().trim().equals("") && !memosForm.isCheckboxSIAG()) {
			try {
				// memosForm.setCheckboxSIAG(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "SIAG ");
				psmtDelete.executeUpdate();
				psmtDelete.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error("Exception " + e.getClass().getName() + " caught with message: GFID =  "+ gfid + e.getMessage());

				e.printStackTrace();
			}
		}

		/** *************************************************************************************************** */
		// MAOT
		/** *************************************************************************************************** */
		if (memosForm.getMaot() != null && (!(memosForm.getMaot().trim().equals("")) || memosForm.isCheckboxMAOT())) {
			String sqlData = null;
			if (memosForm.isCheckboxMAOT() && memosForm.getMaot().trim().equals("")) {
				sqlData = "isChecked";
			} else
				sqlData = memosForm.getMaot();
			int updateRowsCount = 0;
			try {
				String updateSQL = " update IUAAppMemos set Note = ? , LastUpdatedUserId= ? , LastUpdatedDateTime = GetDate()  where GFID = ?  and MemoCode = ?";
				PreparedStatement psmtUpdate = conn.prepareStatement(updateSQL);
				psmtUpdate.setString(1, sqlData);
				psmtUpdate.setString(2, user.getUserName());
				psmtUpdate.setString(3, gfid);
				psmtUpdate.setString(4, "MAOT ");
				updateRowsCount = psmtUpdate.executeUpdate();
				psmtUpdate.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid , e);
				e.printStackTrace();
			}
			try {
				if (updateRowsCount == 0) {
					String insertSQL = " insert into IUAAppMemos (GFID, MemoCode, Note, LastUpdatedUserId, LastUpdatedDateTime)  values ( ?, ? , ? , ?, GetDate() ) ";
					PreparedStatement psmt = conn.prepareStatement(insertSQL);
					psmt.setString(1, gfid);
					psmt.setString(2, "MAOT ");
					psmt.setString(3, sqlData);
					psmt.setString(4, user.getUserName());
					psmt.execute();
					psmt.close();
				}
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed, GFID = " +gfid, e);
				e.printStackTrace();
			}
		}
		if (memosForm.getMaot().trim().equals("") && !memosForm.isCheckboxMAOT()) {
			try {
				// memosForm.setCheckboxMAOT(false);
				String deleteSQL = " delete from IUAAppMemos where GFID = ? and MemoCode= ? ";
				PreparedStatement psmtDelete = conn.prepareStatement(deleteSQL);
				psmtDelete.setString(1, gfid);
				psmtDelete.setString(2, "MAOT ");
				psmtDelete.executeUpdate();
				psmtDelete.close();
			} catch (Exception e) {
				ctx.addGlobalError("error.mainError", "");
				log.error(" Exception: Memo Update Failed , GFID = " + gfid, e);
				e.printStackTrace();
			}
		}

		conn.close();
		
		/*
		 *  Added for USR 8399-1 changes
		 *  on 02-12-2008 by Nagrathna Hiriyurkar  
		 */
		
			OnTabUpdates.MemosAmendTabDataStatus(ctx);
		
		// End of USR 8399-1 .
		log.debug("End update_onClick");
		ctx.forwardByName("success");
	}

}
