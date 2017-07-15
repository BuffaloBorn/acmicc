package com.epm.acmi.maintenance;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epm.acmi.struts.form.dsp.ApplicationVO;
import com.epm.acmi.struts.form.dsp.StellentClient;

public class MaintenanceUpdateDocsNewProducts {
	private static Logger log = Logger.getLogger(MaintenanceUpdateDocsNewProducts.class);
	
	public static void updateStellentDocMetadataFromList(List voList) throws Exception
	{
		StellentClient stellentClient = new StellentClient();
		String AAID = StellentClient.login();
		
		try
		{
			log.debug("Begin update all documents");
			for (int i = 0; i < voList.size(); i++)
			{
				ApplicationVO vo = (ApplicationVO)voList.get(i);
				log.debug("updating metadata with vo: " + vo.toString());
				stellentClient.updateAllDocuments(
						"d446",
						AAID, 
						vo.getGfid(),
						vo.getPolicyNumber(),
						vo.getKeyApplicantFirstName(),
						vo.getKeyApplicantLastName(),
						vo.getKeyApplicantMI(),
						vo.getKeyApplicantSuffix(),
						vo.getAgentName(),
						vo.getAgentNumber(),
						vo.getListBill(),
						vo.getProductName(),
						vo.getState());
			}

		} catch (Exception e) {
			log.debug("Exception " + e.getClass().getName() + " with message: " + e.getMessage() + " thrown while attemping to use Stellent");
		} finally
		{
			if (AAID != null)
				StellentClient.logout(AAID);
		}
		log.debug("End update all documents");
	}
	
	private static List getApplicationVOList() throws Exception
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List voList = new ArrayList();
		
		try
		{
			conn = getConnection();
			
			if (conn == null)
				throw new Exception("DB Connection is null");
			
			stmt = conn.createStatement();
//*
			//Production SQL String
			String sql = "select * from acmi.dbo.IUAApplication app2 where app2.product in('NHSA','NHST','TIER','TTRT')";
//*/
		
/*
			//Development SQL String
			String sql = "select app.* from acmi.dbo.IUAApplication app, StellentImaging.dbo.NewBizMain biz where " +
						"(biz.policyNumber='0' or biz.policyNumber is null) and app.policyNo <> '0' and app.policyNo is not null and " +
						"app.gfid=biz.gfid and app.product = 'HPSAPHSA'";
//*/
		
			rs = stmt.executeQuery(sql);
		
			while (rs.next())
			{
				ApplicationVO vo = new ApplicationVO();
				
				vo.setGfid(rs.getString("GFID"));
				vo.setListBill(rs.getString("IASListBill"));
				vo.setPolicyNumber(rs.getString("PolicyNo"));
				vo.setState(rs.getString("State"));
				vo.setKeyApplicantFirstName(rs.getString("KeyAppFirstName"));
				vo.setKeyApplicantMI(rs.getString("KeyAppMI"));
				vo.setKeyApplicantLastName(rs.getString("KeyAppLastName"));
				vo.setKeyApplicantSuffix(rs.getString("KeyAppSuffix"));
				vo.setAgentNumber(rs.getString("AgentNumber"));
				vo.setAgentName(rs.getString("AgentName"));
				vo.setProductName(rs.getString("Product"));
				log.debug("received date: " + rs.getString("ReceivedDate"));
				
				voList.add(vo);
			}
			
			for (int i = 0; i < voList.size(); i++)
			{
				ApplicationVO vo = (ApplicationVO)voList.get(i);
				log.debug("VO at place " + (i + 1) + ": " + vo.toString());
			}
		} finally
		{

			if (rs != null)
				rs.close();
			
			if (stmt != null)
				stmt.close();
			
			if (conn != null)
				conn.close();
		}
		
		return voList;		
	}
	
	
	private static Connection getConnection() {
		Connection con = null;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (java.lang.ClassNotFoundException e) {
			log.error("ClassNotFoundException: ");
			log.error(e.getMessage());
		}
/*
 		//Development Parameters
		String url = "jdbc:sqlserver://dev5045:1433;databaseName=acmi";
		String userid = "iupsrund";
		String password = "l9>UBC68o-";
//*/
		
//*
 		//Production Parameters
		String url = "jdbc:sqlserver://srv5045:1433;databaseName=acmi";
		String userid = "iupsrunp";
		String password = "4zJ6NYXJ";
//*/
		try {
			con = DriverManager.getConnection(url, userid, password);
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

		return con;
	}

	public  static Object deepClone(Serializable obj)
	{
		if (obj == null)
			return null;
		
	    try
	    {
	    	ByteArrayOutputStream b = new ByteArrayOutputStream();
	    	ObjectOutputStream out = new ObjectOutputStream(b);
	    	out.writeObject(obj);
	    	ByteArrayInputStream byteIn = new ByteArrayInputStream(b.toByteArray());
	    	ObjectInputStream objIn = new ObjectInputStream(byteIn);
	    	return objIn.readObject();
	    } catch (Exception e)
	    {
	    	log.debug(e.getClass().getName() + " exception thrown while cloning object of type " + obj.getClass().getName(), e);
	    	return null;
	    }
	}
	public static void main(String[] args) throws Exception
	{
		List voList = MaintenanceUpdateDocsNewProducts.getApplicationVOList();
		updateStellentDocMetadataFromList(voList);
	}
}