package com.epm.acmi.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.cc.acmi.common.User;

public class LDAPIUAUserUtil {

	private static Logger log = Logger.getLogger(LDAPIUAUserUtil.class);

	private final String selectCountSQL = " select count(EmployeeID) as count from IUAUser WITH (NOLOCK) ";

	private final String selectSQL = " select distinct EMPLOYEEID, EMPLOYEENAME from IUAUser WITH (NOLOCK) WHERE EMPLOYEEID = ? order by EmployeeID ";

	private final String insertSQL = " insert into IUAUser (EmployeeID, EmployeeName) values(?,? ) ";

	private final String updateSQL = " update IUAUser set EmployeeName = ? where EmployeeID = ? ";
	
	
	
	public void getLDAPIUAUserUtil(User logonUser) throws Exception {

		ResultSet selectResultSet = null;
	
		int count = 0;

		// Retrieve cached users map from a list groups
		HashMap users = ACMICache.getUsers();
		Connection conn = null;
		PreparedStatement psmt = null;
			
		try {
			log.debug("Inside LDAPIUAUserUtil.getLDAPIUAUserUtil()method.. ");

			conn = Connect.getConnection();
			psmt = conn.prepareStatement(selectCountSQL);
			selectResultSet = psmt.executeQuery();

			while (selectResultSet.next()) {		
				count = selectResultSet.getInt("count");			
			}

			if (count > 0) {			
				updateLDAPUserInIUAUser(users, logonUser, conn);			
			} else {
				log.debug("The insertLDAPUsersInIUAUser() method is invoked only once... "
								+ "When IUAUser table size is Zero. ");				
				insertLDAPUsersInIUA(users, conn);
			}

		} catch (Exception ex) {			
			log.error("Insert or update in IUAUSER table failed", ex);			
		} finally {			
			Connect.closeResultSet(selectResultSet);		
			Connect.closePSMT(psmt);			
			Connect.closeConnection(conn);			
		}
	}

	private void insertLDAPUsersInIUA(HashMap users, Connection conn) throws Exception {
		log.debug(" Begin inserting data into IUAUSER table....");
		PreparedStatement psmt = null;
		
		try {	
			Iterator itr = users.keySet().iterator();
			psmt = conn.prepareStatement(insertSQL);	
			
			while (itr.hasNext()) {	
				String key = (String) itr.next();	
				LDAPUser loguser = (LDAPUser) users.get(key);	
				String userName = loguser.getFirstName() + " "
						+ loguser.getLastName();	
				String userID = loguser.getUserId().toLowerCase().trim();
			
				psmt.setString(1, userID);				
				psmt.setString(2, userName);				
				psmt.executeUpdate();
			}
			
			log.debug(" Ends inserting data into IUAUSER table....");			
		}catch(Exception e){			
			log.error("error - " +e.getMessage(), e);			
		}finally{			
			Connect.closePSMT(psmt);
		}		
	}

	private void updateLDAPUserInIUAUser(HashMap users, User logonUser, Connection conn) throws Exception {		
		ResultSet selectResultSet1 = null;
		PreparedStatement psmt1 = null;
		PreparedStatement psmt2 = null;
		
		try {
			log.debug("Inside LDAPIUAUserUtil.updateLDAPUserInIUAUser() method...");
	
			psmt1 = conn.prepareStatement(selectSQL);
			
			String userId = logonUser.getUserId().toLowerCase();
			psmt1.setString(1, userId);
			selectResultSet1 = psmt1.executeQuery();	
			String userName = "";
			boolean userExists = false;
			
			while (selectResultSet1.next()) {
				userExists = true;
				userName = selectResultSet1.getString("EmployeeName");	
			}	
	
			if (userExists) {				
				LDAPUser tempUser = (LDAPUser) users.get(userId);								
				String tempUserName = (String)tempUser.getFirstName() + " " + (String)tempUser.getLastName();
						
				if (!tempUserName.trim().equalsIgnoreCase(userName.trim())) {					
					psmt2 = conn.prepareStatement(updateSQL);					
					psmt2.setString(1, tempUserName);					
					psmt2.setString(2, logonUser.getUserId());					
					int updateRows = psmt2.executeUpdate();
					
					if (updateRows == 1)						
						log.debug("UserName in Active Directory & Database are different. Updated employeeNmae in Database Successful..."
										+ logonUser.getUserId() + " - " + tempUserName);	
					}				
			}else {				
				log.debug("User is in Active Directory but does not exist in Database ");				
				psmt2 = conn.prepareStatement(insertSQL);				
				psmt2.setString(1, logonUser.getUserId());				
				psmt2.setString(2, logonUser.getUserName());				
				psmt2.executeUpdate();
			}
		
			log.debug(" Ends updating data into IUAUSER table....");			
		}catch(Exception e){			
			log.error("error - " +e.getMessage(), e);			
		}finally{			
			Connect.closeResultSet(selectResultSet1);			
			Connect.closePSMT(psmt1);	
			Connect.closePSMT(psmt2);
		}	
	}
	
	public static String SelectUsersFromID(String UserId) throws Exception {
		
		log.debug(" Begin SelectUsersFromID() in LDAPIUAUserUtil class...");
		
		String selectEmployeeSQL = "select EmployeeName from IUAUser WITH (NOLOCK) where EmployeeID = '";
		Connection connection = Connect.getConnection();
		Statement stmt = null;
		String employeeName = "";
		ResultSet resultSet = null;
		selectEmployeeSQL = selectEmployeeSQL + UserId + "'" ;
		try {	
			stmt = connection.createStatement();	
			resultSet = stmt.executeQuery(selectEmployeeSQL);
			while (resultSet.next()) {		
				employeeName = resultSet.getString(1);
			}	
			connection.close();
		}catch(Exception e){			
			log.error("error - " +e.getMessage(), e);
			
		}
		log.debug(" End of SelectUsersFromID() in LDAPIUAUserUtil class..." + employeeName);
		return employeeName;
	}

	
}
