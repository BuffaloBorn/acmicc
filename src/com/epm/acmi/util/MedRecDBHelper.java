package com.epm.acmi.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.epm.acmi.struts.form.MedRecForm;
import com.epm.acmi.struts.form.dsp.MedRecDisp;
import com.epm.acmi.struts.form.dsp.MedRecList;

/**
 * MedRecHelper class used for DB action in Medical Records Page
 * 
 * @author Dragos Mandruliano
 */
public class MedRecDBHelper {

	private static Logger log;

	static {
		log = Logger.getLogger(MedRecDBHelper.class);
	}
	
	private String convertToNull(String val) {

		if (val.equals(""))
			return null;

		return val;
	}


	/**
	 * Returns MedRecList from ACMI database
	 * 
	 * @param gfid
	 * @return MedRecList
	 * @throws Exception
	 */
	public MedRecList getListFromDB(String gfid) throws Exception {

		String sql = " select * from IUAMedRecRequest where GFID = ? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rst = null;
		MedRecDisp medRecObj;
		MedRecDisp[] medRecArray = new MedRecDisp[0];
		MedRecList medRecList;
		ArrayList tempList = new ArrayList();

		log.debug("Entering method getListFromDB()");

		try {
			conn = Connect.getConnection();

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, gfid);

			rst = psmt.executeQuery();
			while (rst.next()) {
				medRecObj = new MedRecDisp();
				medRecObj.setFullName(rst.getString("FirstName"), rst.getString("MI"), rst.getString("LastName"), rst
						.getString("Suffix"));
				medRecObj.setReqDate(IUAUtils.convertDateToString(rst.getDate("RequestDate")));
				medRecObj.setAppType(rst.getString("Type"));
				medRecObj.setPhysicianName(rst.getString("PhysicianName"));
				medRecObj.setPhysicianLocated(rst.getString("PhysicianNotLocated"));
				medRecObj.setPhiMissing(rst.getString("PHIAuthMissing"));
				medRecObj.setApplicantID(rst.getString("MedRecId"));
				tempList.add(medRecObj);
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

		medRecArray = (MedRecDisp[]) tempList.toArray(medRecArray);

		log.debug("size of prev policy array " + medRecArray.length);

		medRecList = new MedRecList();
		medRecList.setData(medRecArray);

		log.debug("Exiting method getListFromDB()");

		return medRecList;
	}


	/**
	 * Return MedRecForm from ACMI DB
	 * 
	 * @param gfid
	 * @param applicantID
	 * @return MedRecForm
	 * @throws Exception
	 */
	public MedRecForm getFormFromDB(String gfid, String applicantID) throws Exception {

		String sql = " select * from IUAMedRecRequest where GFID = ? and MedRecId = ? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rst = null;
		MedRecForm form = null;

		log.debug("Entering method getFormFromDB()");

		try {
			conn = Connect.getConnection();

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, gfid);
			psmt.setString(2, applicantID);

			rst = psmt.executeQuery();
			while (rst.next()) {
				form = new MedRecForm();
				form.setFirstName(rst.getString("FirstName"));
				form.setLastName(rst.getString("LastName"));
				form.setInitialMid(rst.getString("MI"));
				form.setSuffix(rst.getString("Suffix"));
				form.setApplicantType(rst.getString("Type"));
				form.setRequestDate(IUAUtils.convertDateToString(rst.getDate("RequestDate")));
				form.setPhysicianName(rst.getString("PhysicianName"));
				form.setPhysicianLocatedFromDB(rst.getString("PhysicianNotLocated"));
				form.setPhiMissingFromDB(rst.getString("PHIAuthMissing"));
				form.setApplicantID(rst.getString("MedRecId"));
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
	 * Delete MedRec from ACMI Database
	 * 
	 * @param gfid
	 * @param applicantID
	 * @throws Exception
	 */
	public void deleteFromDB(String gfid, String applicantID) throws Exception {
		String sql = " delete from IUAMedRecRequest where GFID = ? and MedRecId = ? ";
		Connection conn = null;
		PreparedStatement psmt = null;

		log.debug("Entering method deleteFromDB()");

		try {
			conn = Connect.getConnection();

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, gfid);
			psmt.setString(2, applicantID);

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
	 * Save MedRec to ACMI DB
	 * 
	 * @param gfid
	 * @param form
	 * @param userID
	 * @throws Exception
	 */
	public void saveToDB(String gfid, MedRecForm form, String userID) throws Exception {

		String sqlUpdate = " update IUAMedRecRequest set Suffix = ?, FirstName = ?, LastName = ?, Type = ?, PhysicianName = ?, ";
		sqlUpdate += " PhysicianNotLocated = ?, PHIAuthMissing = ?, LastUpdatedUserId = ?, LastUpdatedDateTime = GetDate(), ";
		sqlUpdate += " RequestDate = ?, MI = ? where MedRecId = ? and GFID = ? ";

		String sqlInsert;
		Connection conn = null;
		PreparedStatement psmt = null;
		int updateRows = 0;

		log.debug("Entering method saveToDB()");

		try {
			conn = Connect.getConnection();

			psmt = conn.prepareStatement(sqlUpdate);
			psmt.setString(1, form.getSuffix());
			psmt.setString(2, form.getFirstName());
			psmt.setString(3, form.getLastName());
			psmt.setString(4, form.getApplicantType());
			psmt.setString(5, form.getPhysicianName());
			psmt.setString(6, form.getPhysicianLocatedForDB());
			psmt.setString(7, form.getPhiMissingForDB());
			psmt.setString(8, userID);
			psmt.setString(9, this.convertToNull(form.getRequestDate()));
			psmt.setString(10, form.getInitialMid());
			psmt.setString(11, form.getApplicantID());
			psmt.setString(12, gfid);

			updateRows = psmt.executeUpdate();

			if (updateRows == 0) {
				sqlInsert = " insert into IUAMedRecRequest (GFID, Suffix, FirstName, LastName, Type, PhysicianName, ";
				sqlInsert += " PhysicianNotLocated, PHIAuthMissing, LastUpdatedUserId, LastUpdatedDateTime, RequestDate, MI) ";
				sqlInsert += " values (?, ?, ?, ?, ?, ?, ?, ?, ?, GetDate(), ?, ?)";

				psmt = conn.prepareStatement(sqlInsert);
				psmt.setString(1, gfid);
				psmt.setString(2, form.getSuffix());
				psmt.setString(3, form.getFirstName());
				psmt.setString(4, form.getLastName());
				psmt.setString(5, form.getApplicantType());
				psmt.setString(6, form.getPhysicianName());
				psmt.setString(7, form.getPhysicianLocatedForDB());
				psmt.setString(8, form.getPhiMissingForDB());
				psmt.setString(9, userID);
				psmt.setString(10, this.convertToNull(form.getRequestDate()));
				psmt.setString(11, form.getInitialMid());

				psmt.executeUpdate();
			}
		} catch (Exception e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " ,GFID = " + gfid);
			e.printStackTrace();
			throw e;
		} finally {
			psmt.close();
			conn.close();
		}

		log.debug("Exiting method saveToDB()");
	}
}
