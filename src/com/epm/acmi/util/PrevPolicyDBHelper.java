package com.epm.acmi.util;

/**
 * PrevPolicyDBHelper used for handling DB actions in Prev policy page Author : Dragos Mandruliano
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.struts.form.PrevPolicyForm;
import com.epm.acmi.struts.form.dsp.PrevPolicyDisp;
import com.epm.acmi.struts.form.dsp.PrevPolicyList;

public class PrevPolicyDBHelper {

	private static Logger log;

	static {
		log = Logger.getLogger(PrevPolicyDBHelper.class);
	}


	private String convertDateToString(Date date) {

		Calendar cal = Calendar.getInstance();
		String retVal;

		log.debug("Entering method convertDateToString()");

		if (date == null)
			retVal = "";
		else {
			cal.setTime(date);
			retVal = "" + (cal.get(Calendar.MONTH) + 1);
			retVal += "/";
			retVal += cal.get(Calendar.DATE);
			retVal += "/";
			retVal += cal.get(Calendar.YEAR);
		}

		log.debug("Exiting method convertDateToString()");

		return retVal;
	}


	/**
	 * Return PrevPolicy list from ACMI DB
	 * 
	 * @param gfid
	 * @return PrevPolicyList
	 * @throws Exception
	 */
	public PrevPolicyList getListFromDB(String gfid) throws Exception {

		String sql = " select * from IUAPrevPolicy WITH (NOLOCK) where GFID = ? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rst = null;
		PrevPolicyDisp prevPolicyObj;
		PrevPolicyDisp[] prevPolicyArray = new PrevPolicyDisp[0];
		PrevPolicyList prevPolicyList;
		ArrayList tempList = new ArrayList();

		log.debug("Entering method getListFromDB()");

		try {
			conn = Connect.getConnection();

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, gfid);

			rst = psmt.executeQuery();
			while (rst.next()) {
				prevPolicyObj = new PrevPolicyDisp();
				prevPolicyObj.setEffDate(this.convertDateToString(rst.getDate("EffectiveDate")));
				prevPolicyObj.setInsuredName(rst.getString("InsuredName"));
				prevPolicyObj.setPreviousPolicyID(rst.getString("PreviousPolicyNo"));
				prevPolicyObj.setPtd(this.convertDateToString(rst.getDate("PaidToDate")));
				prevPolicyObj.setStatus(rst.getString("Status"));
				prevPolicyObj.setShortTermPolicy(rst.getString("ShortTermPolicy"));
				tempList.add(prevPolicyObj);
			}
		} catch (Exception e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " ,GFID = " + gfid);
			e.printStackTrace();
			throw e;
		} finally {
			rst.close();
			psmt.close();
			conn.close();
		}

		prevPolicyArray = (PrevPolicyDisp[]) tempList.toArray(prevPolicyArray);

		prevPolicyList = new PrevPolicyList();
		prevPolicyList.setData(prevPolicyArray);

		log.debug("Exiting method getListFromDB()");

		return prevPolicyList;

	}


	/**
	 * Return Prev Policy Form from ACMI DB
	 * 
	 * @param gfid
	 * @param prevPolicyNum
	 * @return PrevPolicyForm
	 * @throws Exception
	 */
	public PrevPolicyForm getFormFromDB(String gfid, String prevPolicyNum) throws Exception {

		String sql = " select * from IUAPrevPolicy WITH (NOLOCK) where GFID = ? and PreviousPolicyNo = ? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rst = null;
		PrevPolicyForm form = null;

		log.debug("Entering method getFormFromDB()");

		try {
			conn = Connect.getConnection();

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, gfid);
			psmt.setString(2, prevPolicyNum);

			rst = psmt.executeQuery();
			while (rst.next()) {
				form = new PrevPolicyForm();
				form.setEffDate(this.convertDateToString(rst.getDate("EffectiveDate")));
				form.setInsuredName(rst.getString("InsuredName"));
				form.setPreviousPolicyID(rst.getString("PreviousPolicyNo"));
				form.setPtd(this.convertDateToString(rst.getDate("PaidToDate")));
				form.setStatus(rst.getString("Status"));
				form.setShortTermPolicy(rst.getString("ShortTermPolicy"));
			}
		} catch (Exception e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " ,GFID = " + gfid);
			e.printStackTrace();
			throw e;
		} finally {
			if (rst != null)
				rst.close();
			if (psmt != null)
				psmt.close();
			if (conn != null)
				conn.close();
		}

		log.debug("Exiting method getFormFromDB()");

		return form;
	}


	/**
	 * Delete Previous policy from ACMI DB
	 * 
	 * @param gfid
	 * @param prevPolicyNum
	 * @throws Exception
	 */
	public void deleteFromDB(String gfid, String prevPolicyNum) throws Exception {

		String sql = " delete from IUAPrevPolicy where GFID = ? and PreviousPolicyNo = ? ";
		Connection conn = null;
		PreparedStatement psmt = null;

		log.debug("Entering method deleteFromDB()");

		try {
			conn = Connect.getConnection();

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, gfid);
			psmt.setString(2, prevPolicyNum);

			psmt.executeUpdate();
		} catch (Exception e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " ,GFID = " + gfid);
			e.printStackTrace();
			throw e;
		} finally {
			psmt.close();
			conn.close();
		}

		log.debug("Exiting method deleteFromDB()");

	}


	/**
	 * Save Prev Policy to ACMI DB.
	 * 
	 * @param gfid
	 * @param form
	 * @param userID
	 * @throws Exception
	 */
	public void saveToDB(FormActionContext ctx, String gfid, PrevPolicyForm form, String userID) throws Exception {

		String sqlUpdate = " update IUAPrevPolicy set status = ?, EffectiveDate = ?, ";
		sqlUpdate += " PaidToDate = ?, InsuredName = ?, LastUpdatedDateTime = GetDate(), LastUpdatedUserId = ?, ";
		sqlUpdate += " ShortTermPolicy=? where GFID = ? and PreviousPolicyNo = ? ";

		String sqlcheck = "Select count(PreviousPolicyNo) as count from IUAPrevPolicy WITH (NOLOCK) where GFID = ? and PreviousPolicyNo = ?";

		String sqlInsert;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rst = null;
		int updateRows = 0;

		log.debug("Entering method saveToDB()");

		try {
			conn = Connect.getConnection();
			psmt = conn.prepareStatement(sqlcheck);
			psmt.setString(1, gfid);
			psmt.setString(2, form.getPreviousPolicyID());
			rst = psmt.executeQuery();
			int count = 0;
			while (rst.next()) {
				count = rst.getInt("count");

			}

			if (count == 1) {
				// ctx.addGlobalError("prevPolicy.duppolicyError", form.getPreviousPolicyID());
				log.debug(form.getPreviousPolicyID()
						+ " Previous policy entereted aleady exists, so it will be updated with new info");

				psmt = conn.prepareStatement(sqlUpdate);
				psmt.setString(1, form.getStatus());
				psmt.setString(2, this.convertToNull(form.getEffDate()));
				psmt.setString(3, this.convertToNull(form.getPtd()));
				psmt.setString(4, form.getInsuredName());
				psmt.setString(5, userID);
				psmt.setString(6, form.getShortTermPolicy());
				psmt.setString(7, gfid);
				psmt.setString(8, form.getPreviousPolicyID());

				updateRows = psmt.executeUpdate();
			}
			if (updateRows == 0) {
				sqlInsert = "insert into iuaprevpolicy (PreviousPolicyNo, GFID, Status, EffectiveDate, ";
				sqlInsert += " PaidToDate, InsuredName, ShortTermPolicy, LastUpdatedDateTime, LastUpdatedUserId) ";
				sqlInsert += " values (?, ?, ?, ?, ?, ?, ?, GetDate(), ?)";

				psmt = conn.prepareStatement(sqlInsert);
				psmt.setString(1, form.getPreviousPolicyID());
				psmt.setString(2, gfid);
				psmt.setString(3, form.getStatus());
				psmt.setString(4, this.convertToNull(form.getEffDate()));
				psmt.setString(5, this.convertToNull(form.getPtd()));
				psmt.setString(6, form.getInsuredName());
				psmt.setString(7, form.getShortTermPolicy());
				psmt.setString(8, userID);

				psmt.executeUpdate();
			}
		} catch (Exception e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " ,GFID = " + gfid);
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rst)
				rst.close();
			psmt.close();
			conn.close();
		}

		log.debug("Exiting method saveToDB()");
	}


	private String convertToNull(String val) {

		if (val.equals(""))
			return null;

		return val;
	}
}
